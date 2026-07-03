package homies.com.backend.service;

import homies.com.backend.model.Chef;

import java.util.List;

public interface ChefService {

    Chef registerChef(Chef chef);

    Chef getChefById(String id);

    Chef getChefByUserId(String userId);

    List<Chef> getApprovedChefs();

    List<Chef> getChefsByCity(String city);

    Chef updateChef(String id, Chef chef);
}