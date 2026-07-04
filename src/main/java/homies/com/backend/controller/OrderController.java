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

    @GetMapping("/chef/{chefId}")
    public ResponseEntity<List<OrderResponse>> getChefOrders(
            @PathVariable String chefId) {

        return ResponseEntity.ok(orderService.getOrdersByChef(chefId));
    }

}