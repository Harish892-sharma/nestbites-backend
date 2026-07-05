package homies.com.backend.service;

import org.json.JSONObject;

public interface PaymentService {

    JSONObject createOrder(double amount);

    boolean verifyPayment(
            String razorpayOrderId,
            String razorpayPaymentId,
            String razorpaySignature
    );

}