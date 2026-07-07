package homies.com.backend.dto.home;

import homies.com.backend.model.Chef;
import homies.com.backend.model.MenuItem;

import java.util.List;

public class HomeResponse {

    // Chef Sections
    private List<Chef> topRatedChefs;
    private List<Chef> nearbyChefs;
    private List<Chef> newlyJoinedChefs;

    // Food Sections
    private List<MenuItem> popularDishes;
    private List<MenuItem> recommendedDishes;
    private List<MenuItem> todaysSpecials;
    private List<MenuItem> bestSellers;
    private List<MenuItem> healthyMeals;
    private List<MenuItem> homeTiffins;

    // Veg / Non-Veg
    private List<MenuItem> vegMeals;
    private List<MenuItem> nonVegMeals;

    // Meal Time
    private List<MenuItem> breakfast;
    private List<MenuItem> lunch;
    private List<MenuItem> dinner;

    // Categories
    private List<String> categories;

    public HomeResponse() {
    }

    public List<Chef> getTopRatedChefs() {
        return topRatedChefs;
    }

    public void setTopRatedChefs(List<Chef> topRatedChefs) {
        this.topRatedChefs = topRatedChefs;
    }

    public List<Chef> getNearbyChefs() {
        return nearbyChefs;
    }

    public void setNearbyChefs(List<Chef> nearbyChefs) {
        this.nearbyChefs = nearbyChefs;
    }

    public List<Chef> getNewlyJoinedChefs() {
        return newlyJoinedChefs;
    }

    public void setNewlyJoinedChefs(List<Chef> newlyJoinedChefs) {
        this.newlyJoinedChefs = newlyJoinedChefs;
    }

    public List<MenuItem> getPopularDishes() {
        return popularDishes;
    }

    public void setPopularDishes(List<MenuItem> popularDishes) {
        this.popularDishes = popularDishes;
    }

    public List<MenuItem> getRecommendedDishes() {
        return recommendedDishes;
    }

    public void setRecommendedDishes(List<MenuItem> recommendedDishes) {
        this.recommendedDishes = recommendedDishes;
    }

    public List<MenuItem> getTodaysSpecials() {
        return todaysSpecials;
    }

    public void setTodaysSpecials(List<MenuItem> todaysSpecials) {
        this.todaysSpecials = todaysSpecials;
    }

    public List<MenuItem> getBestSellers() {
        return bestSellers;
    }

    public void setBestSellers(List<MenuItem> bestSellers) {
        this.bestSellers = bestSellers;
    }

    public List<MenuItem> getHealthyMeals() {
        return healthyMeals;
    }

    public void setHealthyMeals(List<MenuItem> healthyMeals) {
        this.healthyMeals = healthyMeals;
    }

    public List<MenuItem> getHomeTiffins() {
        return homeTiffins;
    }

    public void setHomeTiffins(List<MenuItem> homeTiffins) {
        this.homeTiffins = homeTiffins;
    }

    public List<MenuItem> getVegMeals() {
        return vegMeals;
    }

    public void setVegMeals(List<MenuItem> vegMeals) {
        this.vegMeals = vegMeals;
    }

    public List<MenuItem> getNonVegMeals() {
        return nonVegMeals;
    }

    public void setNonVegMeals(List<MenuItem> nonVegMeals) {
        this.nonVegMeals = nonVegMeals;
    }

    public List<MenuItem> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(List<MenuItem> breakfast) {
        this.breakfast = breakfast;
    }

    public List<MenuItem> getLunch() {
        return lunch;
    }

    public void setLunch(List<MenuItem> lunch) {
        this.lunch = lunch;
    }

    public List<MenuItem> getDinner() {
        return dinner;
    }

    public void setDinner(List<MenuItem> dinner) {
        this.dinner = dinner;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}