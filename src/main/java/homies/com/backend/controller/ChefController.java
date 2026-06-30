package homies.com.backend.controller;

import homies.com.backend.model.Chef;
import homies.com.backend.model.MenuItem;
import homies.com.backend.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chefs")
public class ChefController {

    @Autowired
    private ChefRepository chefRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<Chef> register(@RequestBody Chef chef) {
        chef.setPassword(passwordEncoder.encode(chef.getPassword()));
        chef.setAvailable(true);
        Chef savedChef = chefRepository.save(chef);
        return ResponseEntity.ok(savedChef);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Chef loginRequest) {
        try {
            Chef chef = chefRepository.findFirstByEmail(loginRequest.getEmail());
            
            if (chef == null) {
                return ResponseEntity.status(404).body("Chef nahi mila!");
            }
            
            if (!passwordEncoder.matches(loginRequest.getPassword(), chef.getPassword())) {
                return ResponseEntity.status(401).body("Password galat hai!");
            }
            
            return ResponseEntity.ok(chef);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllChefs() {
        return ResponseEntity.ok(chefRepository.findAll());
    }

    @PostMapping("/{chefId}/menu")
    public ResponseEntity<?> addMenuItem(@PathVariable String chefId, @RequestBody MenuItem item) {
        Chef chef = chefRepository.findById(chefId).orElse(null);

        if (chef == null) {
            return ResponseEntity.status(404).body("Chef nahi mila!");
        }

        if (chef.getMenu() == null) {
            chef.setMenu(new java.util.ArrayList<>());
        }

        chef.getMenu().add(item);
        chefRepository.save(chef);

        return ResponseEntity.ok(chef);
    }
}