package homies.com.backend.service.thali;

import homies.com.backend.model.thali.Thali;

import java.util.List;

public interface ThaliService {

    Thali addThali(Thali thali);

    List<Thali> getAllThalis();

    List<Thali> getChefThalis(String chefId);

    Thali getThaliById(String id);

    void deleteThali(String id);

}