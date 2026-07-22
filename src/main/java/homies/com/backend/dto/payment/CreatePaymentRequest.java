package homies.com.backend.dto.payment;

public class CreatePaymentRequest {

    // The amount is looked up server-side from this order — never
    // trust an amount sent directly by the client.
    private String orderId;

    public CreatePaymentRequest() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
