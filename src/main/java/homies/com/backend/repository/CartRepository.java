package homies.com.backend.repository;

import homies.com.backend.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<Cart, String> {

    List<Cart> findByUserId(String userId);

    void deleteByUserId(String userId);
}