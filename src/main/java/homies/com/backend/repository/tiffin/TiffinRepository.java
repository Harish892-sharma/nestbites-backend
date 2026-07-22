package homies.com.backend.repository.tiffin;

import homies.com.backend.model.tiffin.Tiffin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TiffinRepository extends MongoRepository<Tiffin,String>{

    List<Tiffin> findByAvailableTrue();

    List<Tiffin> findByChefId(String chefId);

}