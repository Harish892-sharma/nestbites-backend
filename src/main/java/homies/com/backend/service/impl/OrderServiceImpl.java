package homies.com.backend.service.impl;

import homies.com.backend.dto.order.OrderResponse;
import homies.com.backend.dto.order.PlaceOrderRequest;
import homies.com.backend.model.Order;
import homies.com.backend.repository.OrderRepository;
import homies.com.backend.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderResponse placeOrder(PlaceOrderRequest request) {

        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setPaymentMethod(request.getPaymentMethod());

        order.setStatus("PLACED");

        Order savedOrder = orderRepository.save(order);

        OrderResponse response = new OrderResponse();

        response.setOrderId(savedOrder.getId());
        response.setStatus(savedOrder.getStatus());
        response.setCreatedAt(savedOrder.getCreatedAt());
        response.setTotalAmount(savedOrder.getTotalAmount());
        return response;
    }

    @Override
    public List<OrderResponse> getOrdersByUser(String userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        List<OrderResponse> responseList = new ArrayList<>();

        for (Order order : orders) {

            OrderResponse response = new OrderResponse();

            response.setOrderId(order.getId());
            response.setStatus(order.getStatus());
            response.setCreatedAt(order.getCreatedAt());
           response.setTotalAmount(order.getTotalAmount());

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public List<OrderResponse> getOrdersByChef(String chefId) {

        List<Order> orders = orderRepository.findByChefId(chefId);

        List<OrderResponse> responseList = new ArrayList<>();

        for (Order order : orders) {

            OrderResponse response = new OrderResponse();

            response.setOrderId(order.getId());
            response.setStatus(order.getStatus());
            response.setCreatedAt(order.getCreatedAt());
            response.setTotalAmount(order.getTotalAmount());

            responseList.add(response);
        }

        return responseList;
    }
}