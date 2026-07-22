package homies.com.backend.controller;

import homies.com.backend.dto.FavoriteRequest;
import homies.com.backend.dto.FavoriteResponse;
import homies.com.backend.security.CurrentUserUtil;
import homies.com.backend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @PostMapping
    public ResponseEntity<FavoriteResponse> addFavorite(
            @RequestBody FavoriteRequest request) {

        request.setUserId(currentUserUtil.getCurrentUserId());
        return ResponseEntity.ok(favoriteService.addFavorite(request));
    }

    @DeleteMapping
    public ResponseEntity<String> removeFavorite(
            @RequestParam String userId,
            @RequestParam String chefId) {

        currentUserUtil.requireSelfOrAdmin(userId);
        favoriteService.removeFavorite(userId, chefId);

        return ResponseEntity.ok("Removed from favorites.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteResponse>> getFavorites(
            @PathVariable String userId) {

        currentUserUtil.requireSelfOrAdmin(userId);
        return ResponseEntity.ok(favoriteService.getFavorites(userId));
    }
}
