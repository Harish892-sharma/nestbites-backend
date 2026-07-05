package homies.com.backend.dto.payment;

public class CreatePaymentRequest {

    private double amount;

    public CreatePaymentRequest() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}