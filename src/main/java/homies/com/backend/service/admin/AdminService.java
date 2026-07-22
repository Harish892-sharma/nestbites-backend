package homies.com.backend.service.admin;

import homies.com.backend.dto.admin.AdminDashboardResponse;
import homies.com.backend.model.Chef;

import java.util.List;

public interface AdminService {

    AdminDashboardResponse getDashboard();

    List<Chef> getPendingChefs();

    List<Chef> getApprovedChefs();

    Chef approveChef(String chefId);

    Chef rejectChef(String chefId);

    Chef suspendChef(String chefId);

    Chef activateChef(String chefId);
}