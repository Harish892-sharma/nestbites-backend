package homies.com.backend.controller;

import homies.com.backend.dto.order.OrderResponse;
import homies.com.backend.dto.order.PlaceOrderRequest;
import homies.com.backend.dto.order.UpdateOrderStatusRequest;
import homies.com.backend.security.CurrentUserUtil;
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

    @Autowired
    private CurrentUserUtil currentUserUtil;

    // =============================
    // CUSTOMER
    // =============================

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody PlaceOrderRequest request) {

        // The order is always placed as whoever is logged in — the
        // customer can't check out on someone else's account.
        request.setUserId(currentUserUtil.getCurrentUserId());

        return ResponseEntity.ok(orderService.placeOrder(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getUserOrders(
            @PathVariable String userId) {

        currentUserUtil.requireSelfOrAdmin(userId);
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable String orderId) {

        // Only the customer who placed it, the chef fulfilling it, or an
        // admin may view a single order's details.
        String userId = orderService.getOrderUserId(orderId);
        String chefId = orderService.getOrderChefId(orderId);

        if (!currentUserUtil.getCurrentUserId().equals(userId)
                && !currentUserUtil.isAdmin()) {
            currentUserUtil.requireChefOwnerOrAdmin(chefId);
        }

        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable String orderId) {

        currentUserUtil.requireSelfOrAdmin(orderService.getOrderUserId(orderId));
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    // =============================
    // CHEF
    // =============================

    @GetMapping("/chef/{chefId}")
    public ResponseEntity<List<OrderResponse>> getChefOrders(
            @PathVariable String chefId) {

        currentUserUtil.requireChefOwnerOrAdmin(chefId);
        return ResponseEntity.ok(orderService.getOrdersByChef(chefId));
    }

    @PostMapping("/{orderId}/accept")
    public ResponseEntity<OrderResponse> acceptOrder(
            @PathVariable String orderId) {

        currentUserUtil.requireChefOwnerOrAdmin(orderService.getOrderChefId(orderId));
        return ResponseEntity.ok(orderService.acceptOrder(orderId));
    }

    @PostMapping("/{orderId}/reject")
    public ResponseEntity<OrderResponse> rejectOrder(
            @PathVariable String orderId) {

        currentUserUtil.requireChefOwnerOrAdmin(orderService.getOrderChefId(orderId));
        return ResponseEntity.ok(orderService.rejectOrder(orderId));
    }

    @PostMapping("/{orderId}/preparing")
    public ResponseEntity<OrderResponse> preparingOrder(
            @PathVariable String orderId) {

        currentUserUtil.requireChefOwnerOrAdmin(orderService.getOrderChefId(orderId));
        return ResponseEntity.ok(orderService.startPreparing(orderId));
    }

    @PostMapping("/{orderId}/out-for-delivery")
    public ResponseEntity<OrderResponse> outForDelivery(
            @PathVariable String orderId) {

        currentUserUtil.requireChefOwnerOrAdmin(orderService.getOrderChefId(orderId));
        return ResponseEntity.ok(orderService.outForDelivery(orderId));
    }

    @PostMapping("/{orderId}/ready-for-pickup")
    public ResponseEntity<OrderResponse> readyForPickup(
            @PathVariable String orderId) {

        currentUserUtil.requireChefOwnerOrAdmin(orderService.getOrderChefId(orderId));
        return ResponseEntity.ok(orderService.readyForPickup(orderId));
    }

    @PostMapping("/{orderId}/picked-up")
    public ResponseEntity<OrderResponse> markPickedUp(
            @PathVariable String orderId) {

        currentUserUtil.requireChefOwnerOrAdmin(orderService.getOrderChefId(orderId));
        return ResponseEntity.ok(orderService.markPickedUp(orderId));
    }

    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<OrderResponse> deliverOrder(
            @PathVariable String orderId) {

        currentUserUtil.requireChefOwnerOrAdmin(orderService.getOrderChefId(orderId));
        return ResponseEntity.ok(orderService.deliverOrder(orderId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable String orderId,
            @RequestBody UpdateOrderStatusRequest request) {

        currentUserUtil.requireChefOwnerOrAdmin(orderService.getOrderChefId(orderId));
        return ResponseEntity.ok(
                orderService.updateOrderStatus(orderId, request.getStatus())
        );
    }
}
