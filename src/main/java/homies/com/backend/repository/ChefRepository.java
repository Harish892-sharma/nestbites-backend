package homies.com.backend.repository;

import homies.com.backend.model.Chef;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChefRepository extends MongoRepository<Chef, String> {

    Optional<Chef> findByEmail(String email);

    Optional<Chef> findByUserId(String userId);

    List<Chef> findByApprovedTrue();

    List<Chef> findByApprovedFalse();

    List<Chef> findByCityIgnoreCase(String city);

    boolean existsByEmail(String email);

    List<Chef> findByApprovedTrueAndFullNameContainingIgnoreCase(String fullName);
}