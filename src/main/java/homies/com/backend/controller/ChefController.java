package homies.com.backend.controller;

import homies.com.backend.model.Chef;
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

    // Register Chef Profile
    @PostMapping("/register")
    public ResponseEntity<Chef> registerChef(@RequestBody Chef chef) {
        return ResponseEntity.ok(chefService.registerChef(chef));
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
}