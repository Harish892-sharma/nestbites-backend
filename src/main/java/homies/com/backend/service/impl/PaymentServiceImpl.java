package homies.com.backend.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import homies.com.backend.service.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Override
    public JSONObject createOrder(double amount) {

        try {

            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

            JSONObject options = new JSONObject();

            options.put("amount", (int) (amount * 100));
            options.put("currency", "INR");
            options.put("receipt", "order_" + System.currentTimeMillis());

            Order order = razorpay.orders.create(options);

            return new JSONObject(order.toString());

        } catch (Exception e) {

            throw new RuntimeException("Payment Order Creation Failed : " + e.getMessage());

        }
    }

    @Override
    public String getPublicKeyId() {
        return keyId;
    }

    @Override
    public boolean verifyPayment(
            String razorpayOrderId,
            String razorpayPaymentId,
            String razorpaySignature) {

        try {

            JSONObject attributes = new JSONObject();

            attributes.put("razorpay_order_id", razorpayOrderId);
            attributes.put("razorpay_payment_id", razorpayPaymentId);
            attributes.put("razorpay_signature", razorpaySignature);

            return Utils.verifyPaymentSignature(attributes, keySecret);

        } catch (Exception e) {

            return false;

        }
    }
}