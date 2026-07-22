package homies.com.backend.service.search;

import homies.com.backend.model.MenuItem;

import java.util.List;

public interface SearchService {

    List<MenuItem> search(String keyword);

}