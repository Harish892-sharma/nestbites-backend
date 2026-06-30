package homies.com.backend.controller;

import homies.com.backend.model.Order;
import homies.com.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        order.setStatus("pending");
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserOrders(@PathVariable String userId) {
        return ResponseEntity.ok(orderRepository.findByUserId(userId));
    }

    @GetMapping("/chef/{chefId}")
    public ResponseEntity<?> getChefOrders(@PathVariable String chefId) {
        return ResponseEntity.ok(orderRepository.findByChefId(chefId));
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<?> acceptOrder(@PathVariable String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return ResponseEntity.status(404).body("Order nahi mila!");
        order.setStatus("accepted");
        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}/reject")
    public ResponseEntity<?> rejectOrder(@PathVariable String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return ResponseEntity.status(404).body("Order nahi mila!");
        order.setStatus("rejected");
        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<?> deliverOrder(@PathVariable String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return ResponseEntity.status(404).body("Order nahi mila!");
        order.setStatus("delivered");
        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }
}