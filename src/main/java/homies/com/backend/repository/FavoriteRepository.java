package homies.com.backend.repository;

import homies.com.backend.model.favorite.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends MongoRepository<Favorite, String> {

    List<Favorite> findByUserId(String userId);

    Optional<Favorite> findByUserIdAndChefId(String userId, String chefId);

    void deleteByUserIdAndChefId(String userId, String chefId);
}