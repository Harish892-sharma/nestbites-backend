package homies.com.backend.service.impl;

import homies.com.backend.model.MenuItem;
import homies.com.backend.repository.MenuItemRepository;
import homies.com.backend.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public MenuItem addMenuItem(MenuItem menuItem) {
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
        existing.setImageUrl(menuItem.getImageUrl());
        existing.setAvailable(menuItem.isAvailable());

        return menuItemRepository.save(existing);
    }

    @Override
    public void deleteMenuItem(String id) {
        menuItemRepository.deleteById(id);
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