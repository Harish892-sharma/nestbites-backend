package homies.com.backend.service.impl;

import homies.com.backend.dto.FavoriteRequest;
import homies.com.backend.dto.FavoriteResponse;
import homies.com.backend.model.favorite.Favorite;
import homies.com.backend.repository.FavoriteRepository;
import homies.com.backend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Override
    public FavoriteResponse addFavorite(FavoriteRequest request) {

        favoriteRepository
                .findByUserIdAndChefId(request.getUserId(), request.getChefId())
                .ifPresent(f -> {
                    throw new RuntimeException("Chef already added to favorites.");
                });

        Favorite favorite = new Favorite();

        favorite.setUserId(request.getUserId());
        favorite.setChefId(request.getChefId());
        favorite.setChefName(request.getChefName());

        Favorite saved = favoriteRepository.save(favorite);

        return convert(saved);
    }

    @Override
    public void removeFavorite(String userId, String chefId) {
        favoriteRepository.deleteByUserIdAndChefId(userId, chefId);
    }

    @Override
    public List<FavoriteResponse> getFavorites(String userId) {

        List<FavoriteResponse> list = new ArrayList<>();

        for (Favorite favorite : favoriteRepository.findByUserId(userId)) {
            list.add(convert(favorite));
        }

        return list;
    }

    private FavoriteResponse convert(Favorite favorite) {

        FavoriteResponse response = new FavoriteResponse();

        response.setFavoriteId(favorite.getId());
        response.setChefId(favorite.getChefId());
        response.setChefName(favorite.getChefName());
        response.setCreatedAt(favorite.getCreatedAt());

        return response;
    }
}