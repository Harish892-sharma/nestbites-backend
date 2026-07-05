package homies.com.backend.service.impl;

import homies.com.backend.dto.order.OrderResponse;
import homies.com.backend.dto.order.PlaceOrderRequest;
import homies.com.backend.model.Order;
import homies.com.backend.model.enums.OrderStatus;
import homies.com.backend.repository.OrderRepository;
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
        order.setUpdatedAt(new Date());

        return convertToResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse rejectOrder(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(new Date());

        return convertToResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse startPreparing(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.PREPARING);
        order.setUpdatedAt(new Date());

        return convertToResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse outForDelivery(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
        order.setUpdatedAt(new Date());

        return convertToResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse deliverOrder(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.DELIVERED);
        order.setUpdatedAt(new Date());

        return convertToResponse(orderRepository.save(order));
    }

    // ===========================
    // Helper Method
    // ===========================

    private OrderResponse convertToResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setOrderId(order.getId());
        response.setStatus(order.getStatus().name());

        response.setTotalAmount(order.getTotalAmount());

        response.setPaymentStatus(order.getPaymentStatus());

        response.setDeliveryAddress(order.getDeliveryAddress());

        response.setChefName(order.getChefName());
        response.setChefPhone(order.getChefPhone());

        response.setCustomerPhone(order.getCustomerPhone());

        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());

        return response;
    }
}