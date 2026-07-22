package homies.com.backend.service.tiffin;

import homies.com.backend.model.tiffin.Tiffin;

import java.util.List;

public interface TiffinService {

    Tiffin addTiffin(Tiffin tiffin);

    List<Tiffin> getAllTiffins();

    List<Tiffin> getChefTiffins(String chefId);

    Tiffin getTiffinById(String id);

    void deleteTiffin(String id);

}