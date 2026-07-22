package homies.com.backend.service.impl.tiffin;

import homies.com.backend.model.tiffin.Tiffin;
import homies.com.backend.repository.tiffin.TiffinRepository;
import homies.com.backend.service.tiffin.TiffinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TiffinServiceImpl implements TiffinService {

    @Autowired
    private TiffinRepository tiffinRepository;

    @Override
    public Tiffin addTiffin(Tiffin tiffin) {

        tiffin.setCreatedAt(LocalDateTime.now());
        tiffin.setUpdatedAt(LocalDateTime.now());

        return tiffinRepository.save(tiffin);
    }

    @Override
    public List<Tiffin> getAllTiffins() {

        return tiffinRepository.findByAvailableTrue();

    }

    @Override
    public List<Tiffin> getChefTiffins(String chefId) {

        return tiffinRepository.findByChefId(chefId);

    }

    @Override
    public Tiffin getTiffinById(String id) {

        return tiffinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tiffin Not Found"));

    }

    @Override
    public void deleteTiffin(String id) {

        tiffinRepository.deleteById(id);

    }

}