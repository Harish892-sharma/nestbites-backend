package homies.com.backend.repository.thali;

import homies.com.backend.model.thali.Thali;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ThaliRepository extends MongoRepository<Thali, String> {

    List<Thali> findByAvailableTrue();

    List<Thali> findByChefId(String chefId);

}