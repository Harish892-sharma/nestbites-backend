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

        // Approved chefs
        List<Chef> chefs = chefRepository.findByApprovedTrue();

        // Available dishes
        List<MenuItem> dishes = menuItemRepository.findByAvailableTrue();

        response.setTopRatedChefs(chefs);

        response.setPopularDishes(dishes);

        response.setNearbyChefs(chefs);

        response.setRecommendedDishes(dishes);

        Set<String> categorySet = new LinkedHashSet<>();

        for (MenuItem item : dishes) {

            if (item.getCategory() != null &&
                !item.getCategory().isBlank()) {

                categorySet.add(item.getCategory());
            }
        }

        response.setCategories(new ArrayList<>(categorySet));

        return response;
    }
}