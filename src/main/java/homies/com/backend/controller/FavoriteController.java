package homies.com.backend.controller;

import homies.com.backend.dto.FavoriteRequest;
import homies.com.backend.dto.FavoriteResponse;
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

    @PostMapping
    public ResponseEntity<FavoriteResponse> addFavorite(
            @RequestBody FavoriteRequest request) {

        return ResponseEntity.ok(favoriteService.addFavorite(request));
    }

    @DeleteMapping
    public ResponseEntity<String> removeFavorite(
            @RequestParam String userId,
            @RequestParam String chefId) {

        favoriteService.removeFavorite(userId, chefId);

        return ResponseEntity.ok("Removed from favorites.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteResponse>> getFavorites(
            @PathVariable String userId) {

        return ResponseEntity.ok(favoriteService.getFavorites(userId));
    }
}