package homies.com.backend.controller;

import homies.com.backend.model.MenuItem;
import homies.com.backend.security.CurrentUserUtil;
import homies.com.backend.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        // A chef can only ever add items under their own kitchen — never
        // trust a chefId sent from the client.
        menuItem.setChefId(currentUserUtil.getCurrentChefId());
        return ResponseEntity.ok(menuItemService.addMenuItem(menuItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(
            @PathVariable String id,
            @RequestBody MenuItem menuItem) {

        currentUserUtil.requireChefOwnerOrAdmin(
                menuItemService.getMenuItemById(id).getChefId()
        );
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, menuItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable String id) {

        currentUserUtil.requireChefOwnerOrAdmin(
                menuItemService.getMenuItemById(id).getChefId()
        );
        menuItemService.deleteMenuItem(id);

        return ResponseEntity.ok("Menu Item Deleted Successfully");
    }

    // Only food from kitchens within range shows up — same idea as
    // Blinkit/Zepto showing only nearby stores.
    @GetMapping("/nearby")
    public ResponseEntity<List<MenuItem>> getNearbyMenu(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "5") double radiusKm) {

        return ResponseEntity.ok(menuItemService.getNearbyMenu(lat, lng, radiusKm));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItem(@PathVariable String id) {
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }

    @GetMapping("/chef/{chefId}")
    public ResponseEntity<List<MenuItem>> getChefMenu(
            @PathVariable String chefId) {

        return ResponseEntity.ok(menuItemService.getMenuByChef(chefId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuItem>> getAllMenu() {
        return ResponseEntity.ok(menuItemService.getAllAvailableMenu());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getCategoryMenu(
            @PathVariable String category) {

        return ResponseEntity.ok(menuItemService.getMenuByCategory(category));
    }
}