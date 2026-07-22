package homies.com.backend.service.impl.search;

import homies.com.backend.model.MenuItem;
import homies.com.backend.repository.MenuItemRepository;
import homies.com.backend.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public List<MenuItem> search(String keyword) {

        String search = keyword.trim();

        Set<MenuItem> result = new LinkedHashSet<>();

        result.addAll(menuItemRepository.findByNameContainingIgnoreCase(search));

        result.addAll(menuItemRepository.findByCategoryContainingIgnoreCase(search));

        result.addAll(menuItemRepository.findByDescriptionContainingIgnoreCase(search));

        if (search.equalsIgnoreCase("veg")) {
            result.addAll(menuItemRepository.findByVegTrue());
        }

        if (search.equalsIgnoreCase("non veg")
                || search.equalsIgnoreCase("non-veg")) {

            result.addAll(menuItemRepository.findByVegFalse());
        }

        if (search.equalsIgnoreCase("breakfast")) {
            result.addAll(menuItemRepository.findByMealTypeIgnoreCase("BREAKFAST"));
        }

        if (search.equalsIgnoreCase("lunch")) {
            result.addAll(menuItemRepository.findByMealTypeIgnoreCase("LUNCH"));
        }

        if (search.equalsIgnoreCase("dinner")) {
            result.addAll(menuItemRepository.findByMealTypeIgnoreCase("DINNER"));
        }

        if (search.equalsIgnoreCase("healthy")) {
            result.addAll(menuItemRepository.findByHealthyTrue());
        }

        if (search.equalsIgnoreCase("tiffin")) {
            result.addAll(menuItemRepository.findByHomeTiffinTrue());
        }

        if (search.equalsIgnoreCase("special")) {
            result.addAll(menuItemRepository.findByTodaysSpecialTrue());
        }

        if (search.equalsIgnoreCase("bestseller")) {
            result.addAll(menuItemRepository.findByBestsellerTrue());
        }

        return new ArrayList<>(result);
    }
}