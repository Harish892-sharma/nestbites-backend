package homies.com.backend.service;

import homies.com.backend.dto.FavoriteRequest;
import homies.com.backend.dto.FavoriteResponse;

import java.util.List;

public interface FavoriteService {

    FavoriteResponse addFavorite(FavoriteRequest request);

    void removeFavorite(String userId, String chefId);

    List<FavoriteResponse> getFavorites(String userId);
}