package homies.com.backend.repository;

import homies.com.backend.model.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuItemRepository extends MongoRepository<MenuItem, String> {

    // Chef
    List<MenuItem> findByChefId(String chefId);

    // Category
    List<MenuItem> findByCategoryIgnoreCase(String category);

    // Available Food
    List<MenuItem> findByAvailableTrue();

    // Dish Name
    List<MenuItem> findByNameContainingIgnoreCase(String name);

    // Category Search
    List<MenuItem> findByCategoryContainingIgnoreCase(String category);

    // Description Search
    List<MenuItem> findByDescriptionContainingIgnoreCase(String description);

    // Price
    List<MenuItem> findByPriceBetween(double minPrice, double maxPrice);

    // Available + Name
    List<MenuItem> findByAvailableTrueAndNameContainingIgnoreCase(String keyword);

    // Today's Special
    List<MenuItem> findByTodaysSpecialTrue();

    // Best Seller
    List<MenuItem> findByBestsellerTrue();

    // Healthy
    List<MenuItem> findByHealthyTrue();

    // Home Tiffin
    List<MenuItem> findByHomeTiffinTrue();

    // Veg
    List<MenuItem> findByVegTrue();

    // Non Veg
    List<MenuItem> findByVegFalse();

    // Meal Type
    List<MenuItem> findByMealTypeIgnoreCase(String mealType);

    // Chef + Available
    List<MenuItem> findByChefIdAndAvailableTrue(String chefId);
}