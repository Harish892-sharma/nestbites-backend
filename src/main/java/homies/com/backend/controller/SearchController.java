package homies.com.backend.controller;

import homies.com.backend.model.MenuItem;
import homies.com.backend.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<List<MenuItem>> search(
            @RequestParam String keyword
    ) {

        return ResponseEntity.ok(
                searchService.search(keyword)
        );
    }
}