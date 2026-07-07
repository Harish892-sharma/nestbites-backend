package homies.com.backend.service.impl.admin;

import homies.com.backend.dto.admin.AdminDashboardResponse;
import homies.com.backend.model.Chef;
import homies.com.backend.repository.*;
import homies.com.backend.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public AdminDashboardResponse getDashboard() {

        AdminDashboardResponse response = new AdminDashboardResponse();

        response.setTotalUsers(userRepository.count());
        response.setTotalChefs(chefRepository.count());
        response.setApprovedChefs(chefRepository.findByApprovedTrue().size());
        response.setPendingChefs(chefRepository.findByApprovedFalse().size());
        response.setTotalOrders(orderRepository.count());
        response.setTotalMenuItems(menuItemRepository.count());
        response.setTotalReviews(reviewRepository.count());
        response.setTotalRevenue(0.0);

        return response;
    }

    @Override
    public List<Chef> getPendingChefs() {
        return chefRepository.findByApprovedFalse();
    }

    @Override
    public Chef approveChef(String chefId) {

        Chef chef = chefRepository.findById(chefId)
                .orElseThrow(() -> new RuntimeException("Chef not found"));

        chef.setApproved(true);

        return chefRepository.save(chef);
    }

    @Override
    public Chef rejectChef(String chefId) {

        Chef chef = chefRepository.findById(chefId)
                .orElseThrow(() -> new RuntimeException("Chef not found"));

        chefRepository.delete(chef);

        return chef;
    }

    @Override
    public List<Chef> getApprovedChefs() {
        return chefRepository.findByApprovedTrue();
    }

    @Override
    public Chef suspendChef(String chefId) {

        Chef chef = chefRepository.findById(chefId)
                .orElseThrow(() -> new RuntimeException("Chef not found"));

        chef.setActive(false);

        return chefRepository.save(chef);
    }

    @Override
    public Chef activateChef(String chefId) {

        Chef chef = chefRepository.findById(chefId)
                .orElseThrow(() -> new RuntimeException("Chef not found"));

        chef.setActive(true);

        return chefRepository.save(chef);
    }
}