package homies.com.backend.service.home;

import homies.com.backend.dto.home.HomeResponse;

public interface HomeService {

    HomeResponse getHomeData(Double lat, Double lng, Double radiusKm);

}