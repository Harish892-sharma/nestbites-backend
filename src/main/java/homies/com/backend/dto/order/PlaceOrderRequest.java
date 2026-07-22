package homies.com.backend.dto.order;

import homies.com.backend.model.OrderItem;

import java.util.Date;
import java.util.List;

public class PlaceOrderRequest {

    // Customer
    private String userId;
    private String userName;
    private String customerPhone;

    // Chef
    private String chefId;
    private String chefName;
    private String chefPhone;

    // Order
    private List<OrderItem> items;

    private double totalAmount;

    // Delivery
    private String deliveryAddress;
    private double customerLat;
    private double customerLng;

    // If set, the customer wants this order later rather than ASAP —
    // null/absent means "as soon as possible" (the default).
    private Date scheduledFor;

    // Payment
    private String paymentMethod;
    private String paymentStatus;

    public PlaceOrderRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getChefId() {
        return chefId;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public double getCustomerLat() {
        return customerLat;
    }

    public void setCustomerLat(double customerLat) {
        this.customerLat = customerLat;
    }

    public double getCustomerLng() {
        return customerLng;
    }

    public void setCustomerLng(double customerLng) {
        this.customerLng = customerLng;
    }

    public Date getScheduledFor() {
        return scheduledFor;
    }

    public void setScheduledFor(Date scheduledFor) {
        this.scheduledFor = scheduledFor;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}