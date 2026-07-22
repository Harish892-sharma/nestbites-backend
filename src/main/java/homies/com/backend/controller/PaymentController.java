package homies.com.backend.controller;

import homies.com.backend.dto.payment.CreatePaymentRequest;
import homies.com.backend.dto.payment.PaymentResponse;
import homies.com.backend.dto.payment.VerifyPaymentRequest;
import homies.com.backend.security.CurrentUserUtil;
import homies.com.backend.service.OrderService;
import homies.com.backend.service.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    // Create Razorpay Order — amount comes from the real order in the
    // database, never from the client, so it can't be tampered with.
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(
            @RequestBody CreatePaymentRequest request) {

        currentUserUtil.requireSelfOrAdmin(
                orderService.getOrderUserId(request.getOrderId())
        );

        double amount = orderService.getOrderTotalAmount(request.getOrderId());

        JSONObject order = paymentService.createOrder(amount);

        // Razorpay's key_id is a public identifier (safe to expose to the
        // browser — it's not the secret) and is required by the Razorpay
        // Checkout widget on the frontend.
        JSONObject responseBody = new JSONObject();
        responseBody.put("order", order);
        responseBody.put("keyId", paymentService.getPublicKeyId());

        return ResponseEntity.ok(responseBody.toString());
    }

    // Verify Razorpay Payment
    @PostMapping("/verify")
    public ResponseEntity<PaymentResponse> verifyPayment(
            @RequestBody VerifyPaymentRequest request) {

        currentUserUtil.requireSelfOrAdmin(
                orderService.getOrderUserId(request.getOrderId())
        );

        boolean verified = paymentService.verifyPayment(
                request.getRazorpayOrderId(),
                request.getRazorpayPaymentId(),
                request.getRazorpaySignature()
        );

        if (verified) {
            orderService.markOrderPaid(request.getOrderId(), request.getRazorpayPaymentId());

            return ResponseEntity.ok(
                    new PaymentResponse(true, "Payment Verified Successfully")
            );
        }

        return ResponseEntity.badRequest()
                .body(new PaymentResponse(false, "Invalid Payment Signature"));
    }
}
