package homies.com.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class Cart {

    @Id
    private String id;

    // User
    private String userId;

    // Menu
    private String menuItemId;
    private String menuName;

    // Chef
    private String chefId;
    private String chefName;

    // Price & Quantity
    private double price;
    private int quantity;

    // Image
    private String imageUrl;

    public Cart() {
    }

    public Cart(
            String id,
            String userId,
            String menuItemId,
            String menuName,
            String chefId,
            String chefName,
            double price,
            int quantity,
            String imageUrl
    ) {
        this.id = id;
        this.userId = userId;
        this.menuItemId = menuItemId;
        this.menuName = menuName;
        this.chefId = chefId;
        this.chefName = chefName;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

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

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}