package homies.com.backend.service.impl.home;

import homies.com.backend.dto.home.HomeResponse;
import homies.com.backend.model.Chef;
import homies.com.backend.model.MenuItem;
import homies.com.backend.repository.ChefRepository;
import homies.com.backend.repository.MenuItemRepository;
import homies.com.backend.service.home.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public HomeResponse getHomeData() {

        HomeResponse response = new HomeResponse();

        // ================= CHEFS =================

        List<Chef> approvedChefs = chefRepository.findByApprovedTrue();

        response.setTopRatedChefs(approvedChefs);
        response.setNearbyChefs(approvedChefs);
        response.setNewlyJoinedChefs(approvedChefs);

        // ================= MENU =================

        List<MenuItem> availableFoods = menuItemRepository.findByAvailableTrue();

        response.setPopularDishes(availableFoods);
        response.setRecommendedDishes(availableFoods);

        // Today's Special
        response.setTodaysSpecials(
                menuItemRepository.findByTodaysSpecialTrue()
        );

        // Best Seller
        response.setBestSellers(
                menuItemRepository.findByBestsellerTrue()
        );

        // Healthy
        response.setHealthyMeals(
                menuItemRepository.findByHealthyTrue()
        );

        // Home Tiffin
        response.setHomeTiffins(
                menuItemRepository.findByHomeTiffinTrue()
        );

        // Veg
        response.setVegMeals(
                menuItemRepository.findByVegTrue()
        );

        // Non Veg
        response.setNonVegMeals(
                menuItemRepository.findByVegFalse()
        );

        // Breakfast
        response.setBreakfast(
                menuItemRepository.findByMealTypeIgnoreCase("BREAKFAST")
        );

        // Lunch
        response.setLunch(
                menuItemRepository.findByMealTypeIgnoreCase("LUNCH")
        );

        // Dinner
        response.setDinner(
                menuItemRepository.findByMealTypeIgnoreCase("DINNER")
        );

        // ================= CATEGORIES =================

        Set<String> categories = new LinkedHashSet<>();

        for (MenuItem item : availableFoods) {

            if (item.getCategory() != null &&
                    !item.getCategory().isBlank()) {

                categories.add(item.getCategory());
            }
        }

        response.setCategories(new ArrayList<>(categories));

        return response;
    }
}