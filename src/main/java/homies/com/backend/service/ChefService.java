package homies.com.backend.service;

import homies.com.backend.model.Chef;

import java.util.List;

public interface ChefService {

    Chef registerChef(Chef chef);

    Chef getChefById(String id);

    Chef getChefByUserId(String userId);

    List<Chef> getApprovedChefs();

    List<Chef> getPendingChefs();

    List<Chef> getChefsByCity(String city);

    List<Chef> getNearbyChefs(double lat, double lng, double radiusKm);

    Chef updateChef(String id, Chef chef);

    Chef approveChef(String chefId);

    Chef rejectChef(String chefId);
}