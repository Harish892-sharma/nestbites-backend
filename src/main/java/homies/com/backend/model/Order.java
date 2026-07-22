package homies.com.backend.model;

import homies.com.backend.model.enums.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    // ================= CUSTOMER =================
    private String userId;
    private String userName;
    private String customerPhone;

    // ================= CHEF =================
    private String chefId;
    private String chefName;
    private String chefPhone;

    // ================= ORDER =================
    private List<OrderItem> items;
    private double totalAmount;

    // ================= DELIVERY =================
    private String deliveryAddress;
    // Delivery Location
    private double deliveryLatitude;
    private double deliveryLongitude;

    // True when this chef doesn't self-deliver — the customer collects
    // the order themselves, so no delivery fee applies. Snapshotted at
    // order time so it doesn't change retroactively if the chef later
    // flips their delivery setting.
    private boolean pickupOnly;

    // Delivery Charges — 0 when pickupOnly is true
    private double deliveryFee;

    // Null = ASAP (the default). Otherwise the time the customer asked
    // for this order to be ready.
    private Date scheduledFor;

    // Tax
    private double platformFee;

   // Discount
    private double discountAmount;

     // Final Bill
    private double payableAmount;

      // Delivery Time
    private int estimatedDeliveryMinutes;

     // Order Note 
    private String customerNote;

    // ================= PAYMENT =================
    private String paymentMethod;
    private String paymentStatus;

    // ================= STATUS =================
    private OrderStatus status;

    // ================= CONTACT =================
    private boolean contactUnlocked;

    // ================= OTP (Future) =================
    private String deliveryOtp;

    // ================= TIMELINE =================
    private Date createdAt;
    private Date acceptedAt;
    private Date preparingAt;
    private Date outForDeliveryAt;
    private Date readyForPickupAt;
    private Date deliveredAt;
    private Date pickedUpAt;
    private Date cancelledAt;
    private Date updatedAt;

    public Order() {
        this.status = OrderStatus.PENDING;
        this.contactUnlocked = false;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // ================= GETTERS & SETTERS =================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getDeliveryLatitude() {
        return deliveryLatitude;
    }

    public void setDeliveryLatitude(double deliveryLatitude) {
        this.deliveryLatitude = deliveryLatitude;
    }

    public double getDeliveryLongitude() {
        return deliveryLongitude;
    }

    public void setDeliveryLongitude(double deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }

    public boolean isPickupOnly() {
        return pickupOnly;
    }

    public void setPickupOnly(boolean pickupOnly) {
        this.pickupOnly = pickupOnly;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Date getScheduledFor() {
        return scheduledFor;
    }

    public void setScheduledFor(Date scheduledFor) {
        this.scheduledFor = scheduledFor;
    }

    public double getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(double platformFee) {
        this.platformFee = platformFee;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
       this.discountAmount = discountAmount;
    }

    public double getPayableAmount() {
    return payableAmount;
}

    public void setPayableAmount(double payableAmount) {
        this.payableAmount = payableAmount;
    }

    public int getEstimatedDeliveryMinutes() {
        return estimatedDeliveryMinutes;
    }

    public void setEstimatedDeliveryMinutes(int estimatedDeliveryMinutes) {
        this.estimatedDeliveryMinutes = estimatedDeliveryMinutes;
    }

    public String getCustomerNote() {
    return customerNote;
}

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
   }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isContactUnlocked() {
        return contactUnlocked;
    }

    public void setContactUnlocked(boolean contactUnlocked) {
        this.contactUnlocked = contactUnlocked;
    }

    public String getDeliveryOtp() {
        return deliveryOtp;
    }

    public void setDeliveryOtp(String deliveryOtp) {
        this.deliveryOtp = deliveryOtp;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Date acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public Date getPreparingAt() {
        return preparingAt;
    }

    public void setPreparingAt(Date preparingAt) {
        this.preparingAt = preparingAt;
    }

    public Date getOutForDeliveryAt() {
        return outForDeliveryAt;
    }

    public void setOutForDeliveryAt(Date outForDeliveryAt) {
        this.outForDeliveryAt = outForDeliveryAt;
    }

    public Date getReadyForPickupAt() {
        return readyForPickupAt;
    }

    public void setReadyForPickupAt(Date readyForPickupAt) {
        this.readyForPickupAt = readyForPickupAt;
    }

    public Date getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Date deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Date getPickedUpAt() {
        return pickedUpAt;
    }

    public void setPickedUpAt(Date pickedUpAt) {
        this.pickedUpAt = pickedUpAt;
    }

    public Date getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Date cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}