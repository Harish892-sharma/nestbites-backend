package homies.com.backend.dto.home;

import homies.com.backend.model.Chef;
import homies.com.backend.model.MenuItem;

import java.util.List;

public class HomeResponse {

    private List<Chef> topRatedChefs;

    private List<MenuItem> popularDishes;

    private List<Chef> nearbyChefs;

    private List<String> categories;

    private List<MenuItem> recommendedDishes;

    public HomeResponse() {
    }

    public List<Chef> getTopRatedChefs() {
        return topRatedChefs;
    }

    public void setTopRatedChefs(List<Chef> topRatedChefs) {
        this.topRatedChefs = topRatedChefs;
    }

    public List<MenuItem> getPopularDishes() {
        return popularDishes;
    }

    public void setPopularDishes(List<MenuItem> popularDishes) {
        this.popularDishes = popularDishes;
    }

    public List<Chef> getNearbyChefs() {
        return nearbyChefs;
    }

    public void setNearbyChefs(List<Chef> nearbyChefs) {
        this.nearbyChefs = nearbyChefs;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<MenuItem> getRecommendedDishes() {
        return recommendedDishes;
    }

    public void setRecommendedDishes(List<MenuItem> recommendedDishes) {
        this.recommendedDishes = recommendedDishes;
    }
}