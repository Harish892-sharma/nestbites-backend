package homies.com.backend.service.impl;

import homies.com.backend.dto.order.OrderResponse;
import homies.com.backend.dto.order.PlaceOrderRequest;
import homies.com.backend.model.Order;
import homies.com.backend.model.enums.OrderStatus;
import homies.com.backend.repository.OrderRepository;
import homies.com.backend.service.NotificationService;
import homies.com.backend.service.OrderService;
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

    @Override
    public OrderResponse placeOrder(PlaceOrderRequest request) {

        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setUserName(request.getUserName());
        order.setCustomerPhone(request.getCustomerPhone());

        order.setChefId(request.getChefId());
        order.setChefName(request.getChefName());
        order.setChefPhone(request.getChefPhone());

        order.setItems(request.getItems());

        order.setTotalAmount(request.getTotalAmount());

        order.setDeliveryAddress(request.getDeliveryAddress());

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

    private OrderResponse convertToResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setOrderId(order.getId());
        response.setStatus(order.getStatus().name());
        response.setTotalAmount(order.getTotalAmount());

        response.setPaymentStatus(order.getPaymentStatus());
        response.setDeliveryAddress(order.getDeliveryAddress());

        response.setChefName(order.getChefName());

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