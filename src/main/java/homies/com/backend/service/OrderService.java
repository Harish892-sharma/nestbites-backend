package homies.com.backend.service;

import homies.com.backend.dto.order.OrderResponse;
import homies.com.backend.dto.order.PlaceOrderRequest;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(PlaceOrderRequest request);

    List<OrderResponse> getOrdersByUser(String userId);

    List<OrderResponse> getOrdersByChef(String chefId);
}