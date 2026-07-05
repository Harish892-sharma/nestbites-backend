package homies.com.backend.dto.order;

public class OrderContactResponse {

    private String customerName;
    private String customerPhone;

    private String chefName;
    private String chefPhone;

    private boolean contactVisible;

    public OrderContactResponse() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public String getChefPhone() {
        return chefPhone;
    }

    public void setChefPhone(String chefPhone) {
        this.chefPhone = chefPhone;
    }

    public boolean isContactVisible() {
        return contactVisible;
    }

    public void setContactVisible(boolean contactVisible) {
        this.contactVisible = contactVisible;
    }
}