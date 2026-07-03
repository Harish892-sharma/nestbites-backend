package homies.com.backend.repository;

import homies.com.backend.model.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuItemRepository extends MongoRepository<MenuItem, String> {

    List<MenuItem> findByChefId(String chefId);

    List<MenuItem> findByCategoryIgnoreCase(String category);

    List<MenuItem> findByAvailableTrue();
}