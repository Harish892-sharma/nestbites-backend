package homies.com.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chefs")
public class Chef {

    @Id
    private String id;

    // Linked User
    private String userId;

    // Personal Details
    private String fullName;
    private String email;
    private String phone;

    // Kitchen Details
    private String kitchenName;
    private String description;

    // Address
    private String address;
    private String city;
    private String state;
    private String pincode;

    // Location
    private double latitude;
    private double longitude;

    // Delivery — no delivery-partner fleet exists, so each chef decides
    // for themselves: self-deliver (and set their own per-km rate) or
    // pickup only.
    private boolean deliversSelf = false;
    private double deliveryFeePerKm = 0;
    private double maxDeliveryRadiusKm = 3;

    // Documents
    private String fssaiNumber;
    private String gstNumber;

    // Images
    private String profileImage;
    private String kitchenImage;

    // Business
    private double rating = 0.0;
    private int totalReviews = 0;
    private boolean open = true;
    private boolean approved = false;
    private boolean active = true;

    // Date
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // ===================== GETTERS & SETTERS =====================

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isDeliversSelf() {
        return deliversSelf;
    }

    public void setDeliversSelf(boolean deliversSelf) {
        this.deliversSelf = deliversSelf;
    }

    public double getDeliveryFeePerKm() {
        return deliveryFeePerKm;
    }

    public void setDeliveryFeePerKm(double deliveryFeePerKm) {
        this.deliveryFeePerKm = deliveryFeePerKm;
    }

    public double getMaxDeliveryRadiusKm() {
        return maxDeliveryRadiusKm;
    }

    public void setMaxDeliveryRadiusKm(double maxDeliveryRadiusKm) {
        this.maxDeliveryRadiusKm = maxDeliveryRadiusKm;
    }

    public String getFssaiNumber() {
        return fssaiNumber;
    }

    public void setFssaiNumber(String fssaiNumber) {
        this.fssaiNumber = fssaiNumber;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getKitchenImage() {
        return kitchenImage;
    }

    public void setKitchenImage(String kitchenImage) {
        this.kitchenImage = kitchenImage;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}