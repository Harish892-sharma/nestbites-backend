package homies.com.backend.repository;

import homies.com.backend.model.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuItemRepository extends MongoRepository<MenuItem, String> {

    List<MenuItem> findByChefId(String chefId);

    List<MenuItem> findByCategoryIgnoreCase(String category);

    List<MenuItem> findByAvailableTrue();

    // Search by Dish Name
    List<MenuItem> findByNameContainingIgnoreCase(String name);

    // Search by Category
    List<MenuItem> findByCategoryContainingIgnoreCase(String category);

    // Search by Description
    List<MenuItem> findByDescriptionContainingIgnoreCase(String description);

    // Search by Price Range
    List<MenuItem> findByPriceBetween(double minPrice, double maxPrice);

    // Available + Dish Name
    List<MenuItem> findByAvailableTrueAndNameContainingIgnoreCase(String keyword);
}