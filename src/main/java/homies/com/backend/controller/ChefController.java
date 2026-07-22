package homies.com.backend.controller;

import homies.com.backend.model.Chef;
import homies.com.backend.security.CurrentUserUtil;
import homies.com.backend.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chef")
public class ChefController {

    @Autowired
    private ChefService chefService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    // Register Chef Profile
    @PostMapping("/register")
    public ResponseEntity<Chef> registerChef(@RequestBody Chef chef) {
        // Always link the new chef profile to whoever is logged in —
        // never trust a userId sent from the client.
        chef.setUserId(currentUserUtil.getCurrentUserId());
        return ResponseEntity.ok(chefService.registerChef(chef));
    }

    // Get the logged-in chef's own profile — used by the frontend right
    // after login to discover the chef's profile id.
    @GetMapping("/me")
    public ResponseEntity<Chef> getMyProfile() {
        return ResponseEntity.ok(currentUserUtil.getCurrentChef());
    }

    // Get Chef By Id
    @GetMapping("/profile/{id}")
    public ResponseEntity<Chef> getChef(@PathVariable String id) {
        return ResponseEntity.ok(chefService.getChefById(id));
    }

    // Update Chef Profile
    @PutMapping("/update/{id}")
    public ResponseEntity<Chef> updateChef(
            @PathVariable String id,
            @RequestBody Chef chef) {

        currentUserUtil.requireChefOwnerOrAdmin(id);
        return ResponseEntity.ok(chefService.updateChef(id, chef));
    }

    // Get All Approved Chefs
    @GetMapping("/all")
    public ResponseEntity<List<Chef>> getApprovedChefs() {
        return ResponseEntity.ok(chefService.getApprovedChefs());
    }

    // Get Chefs By City
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Chef>> getChefsByCity(
            @PathVariable String city) {

        return ResponseEntity.ok(chefService.getChefsByCity(city));
    }

    // Only chefs within range — same "nearby stores" idea as Blinkit/Zepto
    @GetMapping("/nearby")
    public ResponseEntity<List<Chef>> getNearbyChefs(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "5") double radiusKm) {

        return ResponseEntity.ok(chefService.getNearbyChefs(lat, lng, radiusKm));
    }
}