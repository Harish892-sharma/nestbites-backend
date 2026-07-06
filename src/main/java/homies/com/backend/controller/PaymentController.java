package homies.com.backend.controller;

import homies.com.backend.dto.payment.CreatePaymentRequest;
import homies.com.backend.dto.payment.PaymentResponse;
import homies.com.backend.dto.payment.VerifyPaymentRequest;
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

    // Create Razorpay Order
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(
            @RequestBody CreatePaymentRequest request) {

        JSONObject order = paymentService.createOrder(request.getAmount());

        return ResponseEntity.ok(order.toString());
    }

    // Verify Razorpay Payment
    @PostMapping("/verify")
    public ResponseEntity<PaymentResponse> verifyPayment(
            @RequestBody VerifyPaymentRequest request) {

        boolean verified = paymentService.verifyPayment(
                request.getRazorpayOrderId(),
                request.getRazorpayPaymentId(),
                request.getRazorpaySignature()
        );

        if (verified) {
            return ResponseEntity.ok(
                    new PaymentResponse(true, "Payment Verified Successfully")
            );
        }

        return ResponseEntity.badRequest()
                .body(new PaymentResponse(false, "Invalid Payment Signature"));
    }
}