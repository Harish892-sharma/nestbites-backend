package homies.com.backend.service;

import homies.com.backend.model.MenuItem;

import java.util.List;

public interface MenuItemService {

    MenuItem addMenuItem(MenuItem menuItem);

    MenuItem updateMenuItem(String id, MenuItem menuItem);

    void deleteMenuItem(String id);

    MenuItem getMenuItemById(String id);

    List<MenuItem> getMenuByChef(String chefId);

    List<MenuItem> getAllAvailableMenu();

    List<MenuItem> getMenuByCategory(String category);

    List<MenuItem> getNearbyMenu(double lat, double lng, double radiusKm);
}