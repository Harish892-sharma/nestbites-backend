package homies.com.backend.service.impl.thali;

import homies.com.backend.model.thali.Thali;
import homies.com.backend.repository.thali.ThaliRepository;
import homies.com.backend.service.thali.ThaliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThaliServiceImpl implements ThaliService {

    @Autowired
    private ThaliRepository thaliRepository;

    @Override
    public Thali addThali(Thali thali) {

        thali.setCreatedAt(LocalDateTime.now());
        thali.setUpdatedAt(LocalDateTime.now());

        return thaliRepository.save(thali);
    }

    @Override
    public List<Thali> getAllThalis() {
        return thaliRepository.findByAvailableTrue();
    }

    @Override
    public List<Thali> getChefThalis(String chefId) {
        return thaliRepository.findByChefId(chefId);
    }

    @Override
    public Thali getThaliById(String id) {

        return thaliRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thali Not Found"));

    }

    @Override
    public void deleteThali(String id) {

        thaliRepository.deleteById(id);

    }
}