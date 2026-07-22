package homies.com.backend.service.impl.home;

import homies.com.backend.dto.home.HomeResponse;
import homies.com.backend.model.Chef;
import homies.com.backend.model.MenuItem;
import homies.com.backend.model.enums.OrderStatus;
import homies.com.backend.repository.ChefRepository;
import homies.com.backend.repository.MenuItemRepository;
import homies.com.backend.repository.OrderRepository;
import homies.com.backend.repository.UserRepository;
import homies.com.backend.service.home.HomeService;
import homies.com.backend.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public HomeResponse getHomeData(Double lat, Double lng, Double radiusKm) {

        HomeResponse response = new HomeResponse();

        boolean hasLocation = lat != null && lng != null;
        double effectiveRadius = radiusKm != null ? radiusKm : 5.0;

        // ================= CHEFS =================

        List<Chef> approvedChefs = chefRepository.findByApprovedTrueAndActiveTrue();

        // When the customer has shared their location, only show chefs
        // actually within range — otherwise fall back to showing
        // everyone (e.g. first-time visitor who hasn't granted location
        // access yet).
        List<Chef> visibleChefs = hasLocation
                ? approvedChefs.stream()
                    .filter(chef -> DistanceUtil.distanceKm(lat, lng, chef.getLatitude(), chef.getLongitude()) <= effectiveRadius)
                    .collect(Collectors.toList())
                : approvedChefs;

        response.setTopRatedChefs(
                visibleChefs.stream()
                        .sorted((a, b) -> Double.compare(b.getRating(), a.getRating()))
                        .limit(10)
                        .collect(Collectors.toList())
        );
        response.setNearbyChefs(visibleChefs);
        response.setNewlyJoinedChefs(
                visibleChefs.stream()
                        .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                        .limit(10)
                        .collect(Collectors.toList())
        );

        // ================= MENU (only from visible chefs) =================

        Set<String> visibleChefIds = visibleChefs.stream().map(Chef::getId).collect(Collectors.toSet());

        List<MenuItem> availableFoods = menuItemRepository.findByAvailableTrue().stream()
                .filter(item -> visibleChefIds.contains(item.getChefId()))
                .collect(Collectors.toList());

        response.setPopularDishes(availableFoods);
        response.setRecommendedDishes(availableFoods);

        response.setTodaysSpecials(
                filterVisible(menuItemRepository.findByTodaysSpecialTrue(), visibleChefIds)
        );

        response.setBestSellers(
                filterVisible(menuItemRepository.findByBestsellerTrue(), visibleChefIds)
        );

        response.setHealthyMeals(
                filterVisible(menuItemRepository.findByHealthyTrue(), visibleChefIds)
        );

        response.setHomeTiffins(
                filterVisible(menuItemRepository.findByHomeTiffinTrue(), visibleChefIds)
        );

        response.setVegMeals(
                filterVisible(menuItemRepository.findByVegTrue(), visibleChefIds)
        );

        response.setNonVegMeals(
                filterVisible(menuItemRepository.findByVegFalse(), visibleChefIds)
        );

        response.setBreakfast(
                filterVisible(menuItemRepository.findByMealTypeIgnoreCase("BREAKFAST"), visibleChefIds)
        );

        response.setLunch(
                filterVisible(menuItemRepository.findByMealTypeIgnoreCase("LUNCH"), visibleChefIds)
        );

        response.setDinner(
                filterVisible(menuItemRepository.findByMealTypeIgnoreCase("DINNER"), visibleChefIds)
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

        // ================= REAL PLATFORM STATS =================
        // Always computed from the whole platform (not just what's
        // nearby) so the homepage numbers reflect real, total growth.

        response.setTotalApprovedChefs(chefRepository.findByApprovedTrueAndActiveTrue().size());
        response.setTotalCustomers(userRepository.countByRole("CUSTOMER"));
        response.setTotalOrdersCompleted(
                orderRepository.countByStatusIn(List.of(OrderStatus.DELIVERED, OrderStatus.PICKED_UP))
        );

        List<Chef> ratedChefs = chefRepository.findByApprovedTrueAndActiveTrue().stream()
                .filter(chef -> chef.getTotalReviews() > 0)
                .collect(Collectors.toList());

        double avgRating = ratedChefs.isEmpty()
                ? 0
                : ratedChefs.stream().mapToDouble(Chef::getRating).average().orElse(0);

        response.setAverageChefRating(Math.round(avgRating * 10.0) / 10.0);

        return response;
    }

    private List<MenuItem> filterVisible(List<MenuItem> items, Set<String> visibleChefIds) {
        return items.stream()
                .filter(item -> visibleChefIds.contains(item.getChefId()))
                .collect(Collectors.toList());
    }
}
