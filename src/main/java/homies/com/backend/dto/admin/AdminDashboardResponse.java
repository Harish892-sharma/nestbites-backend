package homies.com.backend.dto.admin;

public class AdminDashboardResponse {

    private long totalUsers;
    private long totalChefs;
    private long approvedChefs;
    private long pendingChefs;
    private long totalOrders;
    private long totalMenuItems;
    private long totalReviews;
    private double totalRevenue;

    public AdminDashboardResponse() {
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalChefs() {
        return totalChefs;
    }

    public void setTotalChefs(long totalChefs) {
        this.totalChefs = totalChefs;
    }

    public long getApprovedChefs() {
        return approvedChefs;
    }

    public void setApprovedChefs(long approvedChefs) {
        this.approvedChefs = approvedChefs;
    }

    public long getPendingChefs() {
        return pendingChefs;
    }

    public void setPendingChefs(long pendingChefs) {
        this.pendingChefs = pendingChefs;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public long getTotalMenuItems() {
        return totalMenuItems;
    }

    public void setTotalMenuItems(long totalMenuItems) {
        this.totalMenuItems = totalMenuItems;
    }

    public long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}