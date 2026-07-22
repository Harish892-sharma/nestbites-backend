package homies.com.backend.service.impl;

import homies.com.backend.exception.BadRequestException;
import homies.com.backend.model.Chef;
import homies.com.backend.repository.ChefRepository;
import homies.com.backend.service.ChefService;
import homies.com.backend.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChefServiceImpl implements ChefService {

    @Autowired
    private ChefRepository chefRepository;

    @Override
    public Chef registerChef(Chef chef) {

        if (chefRepository.findByUserId(chef.getUserId()).isPresent()) {
            throw new BadRequestException("This account already has a kitchen profile.");
        }

        if (chefRepository.existsByEmail(chef.getEmail())) {
            throw new BadRequestException("Chef already registered with this email.");
        }

        chef.setApproved(false);
        chef.setActive(true);
        chef.setOpen(true);
        chef.setCreatedAt(LocalDateTime.now());
        chef.setUpdatedAt(LocalDateTime.now());

        return chefRepository.save(chef);
    }

    @Override
    public Chef getChefById(String id) {
        return chefRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chef not found"));
    }

    @Override
    public Chef getChefByUserId(String userId) {
        return chefRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Chef not found"));
    }

    @Override
    public List<Chef> getApprovedChefs() {
        return chefRepository.findByApprovedTrue();
    }

    @Override
    public List<Chef> getPendingChefs() {
        return chefRepository.findByApprovedFalse();
    }

    @Override
    public List<Chef> getChefsByCity(String city) {
        return chefRepository.findByCityIgnoreCase(city);
    }

    @Override
    public Chef updateChef(String id, Chef updatedChef) {

        Chef chef = getChefById(id);

        chef.setFullName(updatedChef.getFullName());
        chef.setPhone(updatedChef.getPhone());
        chef.setKitchenName(updatedChef.getKitchenName());
        chef.setDescription(updatedChef.getDescription());
        chef.setAddress(updatedChef.getAddress());
        chef.setCity(updatedChef.getCity());
        chef.setState(updatedChef.getState());
        chef.setPincode(updatedChef.getPincode());
        chef.setLatitude(updatedChef.getLatitude());
        chef.setLongitude(updatedChef.getLongitude());
        chef.setFssaiNumber(updatedChef.getFssaiNumber());
        chef.setGstNumber(updatedChef.getGstNumber());
        chef.setProfileImage(updatedChef.getProfileImage());
        chef.setKitchenImage(updatedChef.getKitchenImage());

        chef.setUpdatedAt(LocalDateTime.now());

        return chefRepository.save(chef);
    }

    @Override
    public Chef approveChef(String chefId) {

        Chef chef = getChefById(chefId);

        chef.setApproved(true);
        chef.setUpdatedAt(LocalDateTime.now());

        return chefRepository.save(chef);
    }

    @Override
    public Chef rejectChef(String chefId) {

        Chef chef = getChefById(chefId);

        chefRepository.delete(chef);

        return chef;
    }

    @Override
    public List<Chef> getNearbyChefs(double lat, double lng, double radiusKm) {

        return chefRepository.findByApprovedTrueAndActiveTrue().stream()
                .filter(chef -> DistanceUtil.distanceKm(lat, lng, chef.getLatitude(), chef.getLongitude()) <= radiusKm)
                .collect(Collectors.toList());
    }
}