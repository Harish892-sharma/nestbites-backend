package homies.com.backend.repository;

import homies.com.backend.model.Chef;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends MongoRepository<Chef, String> {
    Chef findFirstByEmail(String email);
}