package homies.com.backend.service.impl;

import homies.com.backend.dto.order.OrderResponse;
import homies.com.backend.dto.order.PlaceOrderRequest;
import homies.com.backend.exception.BadRequestException;
import homies.com.backend.model.MenuItem;
import homies.com.backend.model.Order;
import homies.com.backend.model.OrderItem;
import homies.com.backend.model.enums.OrderStatus;
import homies.com.backend.model.Chef;
import homies.com.backend.model.thali.Thali;
import homies.com.backend.model.tiffin.Tiffin;
import homies.com.backend.repository.ChefRepository;
import homies.com.backend.repository.OrderRepository;
import homies.com.backend.service.MenuItemService;
import homies.com.backend.service.NotificationService;
import homies.com.backend.service.OrderService;
import homies.com.backend.service.thali.ThaliService;
import homies.com.backend.service.tiffin.TiffinService;
import homies.com.backend.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private TiffinService tiffinService;

    @Autowired
    private ThaliService thaliService;

    @Autowired
    private ChefRepository chefRepository;

    /**
     * A cart item can be a regular menu item, a tiffin subscription, or a
     * thali — three separate collections. This looks the id up across all
     * three and returns its verified name/price/chef, so pricing is always
     * authoritative no matter which kind of item was ordered.
     */
    private static class VerifiedItem {
        String name;
        double price;
        String chefId;
    }

    private VerifiedItem lookupItem(String id) {
        VerifiedItem v = new VerifiedItem();

        try {
            MenuItem menuItem = menuItemService.getMenuItemById(id);
            v.name = menuItem.getName();
            v.price = menuItem.getFinalPrice() > 0 ? menuItem.getFinalPrice() : menuItem.getPrice();
            v.chefId = menuItem.getChefId();
            return v;
        } catch (Exception ignored) { }

        try {
            Tiffin tiffin = tiffinService.getTiffinById(id);
            v.name = tiffin.getTitle();
            v.price = tiffin.getPrice();
            v.chefId = tiffin.getChefId();
            return v;
        } catch (Exception ignored) { }

        try {
            Thali thali = thaliService.getThaliById(id);
            v.name = thali.getName();
            v.price = thali.getPrice();
            v.chefId = thali.getChefId();
            return v;
        } catch (Exception ignored) { }

        throw new BadRequestException("One of the items in your order no longer exists.");
    }

    @Override
    public OrderResponse placeOrder(PlaceOrderRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("Your order has no items.");
        }

        // SECURITY: never trust prices sent by the client — recompute
        // every line item (and the total) from the real records in the
        // database. Without this, anyone could edit the request in their
        // browser and pay whatever they want.
        List<OrderItem> verifiedItems = new ArrayList<>();
        double verifiedTotal = 0;

        for (OrderItem requested : request.getItems()) {

            VerifiedItem verifiedSource = lookupItem(requested.getMenuItemId());

            if (!verifiedSource.chefId.equals(request.getChefId())) {
                throw new BadRequestException(
                        "This item doesn't belong to the selected kitchen.");
            }

            if (requested.getQuantity() < 1) {
                throw new BadRequestException("Item quantity must be at least 1.");
            }

            OrderItem verified = new OrderItem();
            verified.setMenuItemId(requested.getMenuItemId());
            verified.setItemName(verifiedSource.name);
            verified.setQuantity(requested.getQuantity());
            verified.setPrice(verifiedSource.price);

            verifiedItems.add(verified);
            verifiedTotal += verifiedSource.price * requested.getQuantity();
        }

        Chef chef = chefRepository.findById(request.getChefId())
                .orElseThrow(() -> new BadRequestException("Selected kitchen not found."));

        // ---- Delivery: chef decides self-delivery vs pickup-only ----
        double distanceKm = DistanceUtil.distanceKm(
                request.getCustomerLat(), request.getCustomerLng(),
                chef.getLatitude(), chef.getLongitude()
        );

        boolean pickupOnly = !chef.isDeliversSelf();
        double deliveryFee = 0;

        if (chef.isDeliversSelf()) {
            if (distanceKm > chef.getMaxDeliveryRadiusKm()) {
                throw new BadRequestException(
                        "This kitchen only delivers within " + chef.getMaxDeliveryRadiusKm() + " km — you're "
                                + Math.round(distanceKm * 10.0) / 10.0 + " km away.");
            }
            deliveryFee = Math.round(chef.getDeliveryFeePerKm() * distanceKm * 100.0) / 100.0;
        }

        double payableAmount = verifiedTotal + deliveryFee;

        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setUserName(request.getUserName());
        order.setCustomerPhone(request.getCustomerPhone());

        order.setChefId(chef.getId());
        order.setChefName(chef.getKitchenName());
        order.setChefPhone(chef.getPhone());

        order.setItems(verifiedItems);

        order.setTotalAmount(verifiedTotal);
        order.setDeliveryFee(deliveryFee);
        order.setPayableAmount(payableAmount);
        order.setPickupOnly(pickupOnly);

        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setDeliveryLatitude(request.getCustomerLat());
        order.setDeliveryLongitude(request.getCustomerLng());
        order.setScheduledFor(request.getScheduledFor());

        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus(request.getPaymentStatus());

        order.setStatus(OrderStatus.PENDING);

        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());

        Order savedOrder = orderRepository.save(order);

        // Notify Chef
        notificationService.sendNotification(
                savedOrder.getChefId(),
                "CHEF",
                "New Order",
                "You received a new order from " + savedOrder.getUserName(),
                "ORDER",
                savedOrder.getId()
        );

        return convertToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getOrdersByUser(String userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        List<OrderResponse> responseList = new ArrayList<>();

        for (Order order : orders) {
            responseList.add(convertToResponse(order));
        }

        return responseList;
    }

    @Override
    public List<OrderResponse> getOrdersByChef(String chefId) {

        List<Order> orders = orderRepository.findByChefId(chefId);

        List<OrderResponse> responseList = new ArrayList<>();

        for (Order order : orders) {
            responseList.add(convertToResponse(order));
        }

        return responseList;
    }

    @Override
    public OrderResponse acceptOrder(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.ACCEPTED);
        order.setContactUnlocked(true);

        order.setAcceptedAt(new Date());
        order.setUpdatedAt(new Date());

        Order updatedOrder = orderRepository.save(order);

        // Notify Customer
        notificationService.sendNotification(
                updatedOrder.getUserId(),
                "CUSTOMER",
                "Order Accepted",
                "Chef accepted your order.",
                "ORDER",
                updatedOrder.getId()
        );

        return convertToResponse(updatedOrder);
    }

    @Override
    public OrderResponse cancelOrder(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Once the chef has actually started cooking, it's too late for
        // a self-serve cancel — the customer needs to sort it out with
        // the kitchen directly at that point.
        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.ACCEPTED) {
            throw new BadRequestException(
                    "This order is already being prepared and can't be cancelled here.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelledAt(new Date());
        order.setUpdatedAt(new Date());

        Order updatedOrder = orderRepository.save(order);

        notificationService.sendNotification(
                updatedOrder.getChefId(),
                "CHEF",
                "Order Cancelled",
                "The customer cancelled order #" + updatedOrder.getId().substring(Math.max(0, updatedOrder.getId().length() - 6)) + ".",
                "ORDER",
                updatedOrder.getId()
        );

        return convertToResponse(updatedOrder);
    }

    @Override
    public OrderResponse rejectOrder(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelledAt(new Date());
        order.setUpdatedAt(new Date());

        Order updatedOrder = orderRepository.save(order);

        notificationService.sendNotification(
                updatedOrder.getUserId(),
                "CUSTOMER",
                "Order Rejected",
                "Unfortunately your order was rejected.",
                "ORDER",
                updatedOrder.getId()
        );

        return convertToResponse(updatedOrder);
    }

    @Override
    public OrderResponse startPreparing(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.PREPARING);
        order.setPreparingAt(new Date());
        order.setUpdatedAt(new Date());

        Order updatedOrder = orderRepository.save(order);

        notificationService.sendNotification(
                updatedOrder.getUserId(),
                "CUSTOMER",
                "Preparing Order",
                "Chef has started preparing your food.",
                "ORDER",
                updatedOrder.getId()
        );

        return convertToResponse(updatedOrder);
    }

    @Override
    public OrderResponse outForDelivery(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
        order.setOutForDeliveryAt(new Date());
        order.setUpdatedAt(new Date());

        Order updatedOrder = orderRepository.save(order);

        notificationService.sendNotification(
                updatedOrder.getUserId(),
                "CUSTOMER",
                "Out For Delivery",
                "Your order is on the way.",
                "ORDER",
                updatedOrder.getId()
        );

        return convertToResponse(updatedOrder);
    }

    @Override
    public OrderResponse readyForPickup(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.READY_FOR_PICKUP);
        order.setReadyForPickupAt(new Date());
        order.setUpdatedAt(new Date());

        Order updatedOrder = orderRepository.save(order);

        notificationService.sendNotification(
                updatedOrder.getUserId(),
                "CUSTOMER",
                "Ready for Pickup",
                "Your order is ready — you can collect it now.",
                "ORDER",
                updatedOrder.getId()
        );

        return convertToResponse(updatedOrder);
    }

    @Override
    public OrderResponse markPickedUp(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.PICKED_UP);
        order.setPickedUpAt(new Date());
        order.setUpdatedAt(new Date());

        Order updatedOrder = orderRepository.save(order);

        notificationService.sendNotification(
                updatedOrder.getUserId(),
                "CUSTOMER",
                "Order Complete",
                "Hope you enjoy your meal! Thanks for ordering.",
                "ORDER",
                updatedOrder.getId()
        );

        return convertToResponse(updatedOrder);
    }

    @Override
    public OrderResponse deliverOrder(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.DELIVERED);
        order.setDeliveredAt(new Date());
        order.setUpdatedAt(new Date());

        Order updatedOrder = orderRepository.save(order);

        notificationService.sendNotification(
                updatedOrder.getUserId(),
                "CUSTOMER",
                "Order Delivered",
                "Enjoy your meal ❤️",
                "ORDER",
                updatedOrder.getId()
        );

        return convertToResponse(updatedOrder);
    }

    @Override
    public OrderResponse updateOrderStatus(String orderId, String status) {

        OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());

        switch (newStatus) {

            case ACCEPTED:
                return acceptOrder(orderId);

            case PREPARING:
                return startPreparing(orderId);

            case OUT_FOR_DELIVERY:
                return outForDelivery(orderId);

            case READY_FOR_PICKUP:
                return readyForPickup(orderId);

            case PICKED_UP:
                return markPickedUp(orderId);

            case DELIVERED:
                return deliverOrder(orderId);

            case CANCELLED:
                return rejectOrder(orderId);

            default:
                throw new RuntimeException("Invalid Status");
        }
    }

    @Override
    public OrderResponse getOrder(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return convertToResponse(order);
    }

    @Override
    public String getOrderUserId(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"))
                .getUserId();
    }

    @Override
    public String getOrderChefId(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"))
                .getChefId();
    }

    @Override
    public double getOrderTotalAmount(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"))
                .getPayableAmount();
    }

    @Override
    public void markOrderPaid(String orderId, String razorpayPaymentId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentStatus("PAID");
        order.setUpdatedAt(new Date());
        orderRepository.save(order);
    }

    private OrderResponse convertToResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setOrderId(order.getId());
        response.setStatus(order.getStatus().name());

        response.setItems(order.getItems());

        response.setTotalAmount(order.getTotalAmount());
        response.setDeliveryFee(order.getDeliveryFee());
        response.setPayableAmount(order.getPayableAmount());
        response.setPickupOnly(order.isPickupOnly());

        response.setPaymentMethod(order.getPaymentMethod());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setDeliveryAddress(order.getDeliveryAddress());
        response.setScheduledFor(order.getScheduledFor());

        response.setChefName(order.getChefName());

        response.setCustomerName(order.getUserName());

        if (order.isContactUnlocked()) {
            response.setChefPhone(order.getChefPhone());
            response.setCustomerPhone(order.getCustomerPhone());
        } else {
            response.setChefPhone(null);
            response.setCustomerPhone(null);
        }

        response.setContactUnlocked(order.isContactUnlocked());

        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());

        return response;
    }
}