package homies.com.backend.service;

import homies.com.backend.dto.order.OrderResponse;
import homies.com.backend.dto.order.PlaceOrderRequest;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(PlaceOrderRequest request);

    List<OrderResponse> getOrdersByUser(String userId);

    List<OrderResponse> getOrdersByChef(String chefId);

    OrderResponse acceptOrder(String orderId);

    OrderResponse rejectOrder(String orderId);

    OrderResponse cancelOrder(String orderId);

    OrderResponse startPreparing(String orderId);

    OrderResponse outForDelivery(String orderId);

    OrderResponse readyForPickup(String orderId);

    OrderResponse markPickedUp(String orderId);

    OrderResponse deliverOrder(String orderId);

    OrderResponse updateOrderStatus(String orderId, String status);
    
    OrderResponse getOrder(String orderId);

    String getOrderUserId(String orderId);

    String getOrderChefId(String orderId);

    double getOrderTotalAmount(String orderId);

    void markOrderPaid(String orderId, String razorpayPaymentId);
}