package homies.com.backend.controller.home;

import homies.com.backend.dto.home.HomeResponse;
import homies.com.backend.service.home.HomeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping
    public ResponseEntity<HomeResponse> getHome() {

        return ResponseEntity.ok(homeService.getHomeData());
    }
}