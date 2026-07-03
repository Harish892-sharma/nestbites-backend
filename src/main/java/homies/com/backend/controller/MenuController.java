package homies.com.backend.controller;

import homies.com.backend.model.MenuItem;
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

    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        return ResponseEntity.ok(menuItemService.addMenuItem(menuItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(
            @PathVariable String id,
            @RequestBody MenuItem menuItem) {

        return ResponseEntity.ok(menuItemService.updateMenuItem(id, menuItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable String id) {

        menuItemService.deleteMenuItem(id);

        return ResponseEntity.ok("Menu Item Deleted Successfully");
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