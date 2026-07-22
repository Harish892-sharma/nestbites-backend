package homies.com.backend.service.impl;

import homies.com.backend.model.Chef;
import homies.com.backend.model.MenuItem;
import homies.com.backend.repository.ChefRepository;
import homies.com.backend.repository.MenuItemRepository;
import homies.com.backend.service.MenuItemService;
import homies.com.backend.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private ChefRepository chefRepository;

    @Override
    public List<MenuItem> getNearbyMenu(double lat, double lng, double radiusKm) {

        // Only chefs who are approved, active, and physically within
        // range show up — this is the "Blinkit-style" nearby filter so
        // customers never see a kitchen too far away to realistically
        // order from.
        Set<String> nearbyChefIds = chefRepository.findByApprovedTrueAndActiveTrue().stream()
                .filter(chef -> DistanceUtil.distanceKm(lat, lng, chef.getLatitude(), chef.getLongitude()) <= radiusKm)
                .map(Chef::getId)
                .collect(Collectors.toSet());

        return menuItemRepository.findByAvailableTrue().stream()
                .filter(item -> nearbyChefIds.contains(item.getChefId()))
                .collect(Collectors.toList());
    }

    @Override
    public MenuItem addMenuItem(MenuItem menuItem) {

        if (menuItem.getChefId() == null || menuItem.getChefId().isBlank()) {
            throw new RuntimeException("Chef Id is required");
        }

        if (menuItem.getName() == null || menuItem.getName().isBlank()) {
            throw new RuntimeException("Food name is required");
        }

        if (menuItem.getPrice() <= 0) {
            throw new RuntimeException("Price must be greater than zero");
        }

        menuItem.setFinalPrice(menuItem.getPrice() - menuItem.getDiscount());
        menuItem.setAvailable(true);
        menuItem.setCreatedAt(LocalDateTime.now());
        menuItem.setUpdatedAt(LocalDateTime.now());

        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(String id, MenuItem menuItem) {

        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        existing.setName(menuItem.getName());
        existing.setDescription(menuItem.getDescription());
        existing.setCategory(menuItem.getCategory());

        existing.setPrice(menuItem.getPrice());
        existing.setDiscount(menuItem.getDiscount());
        existing.setFinalPrice(menuItem.getPrice() - menuItem.getDiscount());

        existing.setServingSize(menuItem.getServingSize());

        existing.setVeg(menuItem.isVeg());
        existing.setSpiceLevel(menuItem.getSpiceLevel());
        existing.setCuisine(menuItem.getCuisine());
        existing.setCalories(menuItem.getCalories());

        existing.setHomemade(menuItem.isHomemade());
        existing.setTodaysSpecial(menuItem.isTodaysSpecial());
        existing.setBestseller(menuItem.isBestseller());

        existing.setPreparationTime(menuItem.getPreparationTime());
        existing.setStock(menuItem.getStock());

        existing.setImageUrl(menuItem.getImageUrl());

        existing.setAvailable(menuItem.isAvailable());
        existing.setActive(menuItem.isActive());

        existing.setIngredients(menuItem.getIngredients());
        existing.setTags(menuItem.getTags());

        existing.setUpdatedAt(LocalDateTime.now());

        return menuItemRepository.save(existing);
    }

    @Override
    public void deleteMenuItem(String id) {

        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        menuItemRepository.delete(existing);
    }

    @Override
    public MenuItem getMenuItemById(String id) {

        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
    }

    @Override
    public List<MenuItem> getMenuByChef(String chefId) {
        return menuItemRepository.findByChefId(chefId);
    }

    @Override
    public List<MenuItem> getAllAvailableMenu() {
        return menuItemRepository.findByAvailableTrue();
    }

    @Override
    public List<MenuItem> getMenuByCategory(String category) {
        return menuItemRepository.findByCategoryIgnoreCase(category);
    }
}