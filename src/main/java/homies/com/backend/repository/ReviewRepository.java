package homies.com.backend.repository;

import homies.com.backend.model.review.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByChefId(String chefId);

    List<Review> findByUserId(String userId);

    boolean existsByOrderId(String orderId);
}