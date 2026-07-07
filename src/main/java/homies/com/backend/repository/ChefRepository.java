package homies.com.backend.repository;

import homies.com.backend.model.Chef;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChefRepository extends MongoRepository<Chef, String> {

    Optional<Chef> findByEmail(String email);

    Optional<Chef> findByUserId(String userId);

    boolean existsByEmail(String email);

    // ================= Approval =================

    List<Chef> findByApprovedTrue();

    List<Chef> findByApprovedFalse();

    // ================= Active =================

    List<Chef> findByActiveTrue();

    List<Chef> findByActiveFalse();

    // ================= Approved + Active =================

    List<Chef> findByApprovedTrueAndActiveTrue();

    // ================= Search =================

    List<Chef> findByCityIgnoreCase(String city);

    List<Chef> findByApprovedTrueAndFullNameContainingIgnoreCase(String fullName);

    List<Chef> findByApprovedTrueAndCityIgnoreCase(String city);

    // ================= Rating =================

    List<Chef> findTop10ByApprovedTrueOrderByRatingDesc();

}