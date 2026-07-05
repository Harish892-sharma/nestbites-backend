package homies.com.backend.controller;

import homies.com.backend.dto.order.OrderResponse;
import homies.com.backend.dto.order.PlaceOrderRequest;
import homies.com.backend.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // =============================
    // CUSTOMER
    // =============================

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody PlaceOrderRequest request) {

        return ResponseEntity.ok(orderService.placeOrder(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getUserOrders(
            @PathVariable String userId) {

        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    // =============================
    // CHEF
    // =============================

    @GetMapping("/chef/{chefId}")
    public ResponseEntity<List<OrderResponse>> getChefOrders(
            @PathVariable String chefId) {

        return ResponseEntity.ok(orderService.getOrdersByChef(chefId));
    }

    @PostMapping("/{orderId}/accept")
    public ResponseEntity<OrderResponse> acceptOrder(
            @PathVariable String orderId) {

        return ResponseEntity.ok(orderService.acceptOrder(orderId));
    }

    @PostMapping("/{orderId}/reject")
    public ResponseEntity<OrderResponse> rejectOrder(
            @PathVariable String orderId) {

        return ResponseEntity.ok(orderService.rejectOrder(orderId));
    }

    @PostMapping("/{orderId}/preparing")
    public ResponseEntity<OrderResponse> preparingOrder(
            @PathVariable String orderId) {

        return ResponseEntity.ok(orderService.startPreparing(orderId));
    }

    @PostMapping("/{orderId}/out-for-delivery")
    public ResponseEntity<OrderResponse> outForDelivery(
            @PathVariable String orderId) {

        return ResponseEntity.ok(orderService.outForDelivery(orderId));
    }

    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<OrderResponse> deliverOrder(
            @PathVariable String orderId) {

        return ResponseEntity.ok(orderService.deliverOrder(orderId));
    }
}