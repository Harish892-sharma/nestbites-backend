package homies.com.backend.controller.admin;

import homies.com.backend.dto.admin.AdminDashboardResponse;
import homies.com.backend.model.Chef;
import homies.com.backend.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> dashboard() {
        return ResponseEntity.ok(adminService.getDashboard());
    }

    @GetMapping("/chef/pending")
    public ResponseEntity<List<Chef>> pendingChefs() {
        return ResponseEntity.ok(adminService.getPendingChefs());
    }

    @GetMapping("/chef/approved")
    public ResponseEntity<List<Chef>> approvedChefs() {
        return ResponseEntity.ok(adminService.getApprovedChefs());
    }

    @PutMapping("/chef/{chefId}/approve")
    public ResponseEntity<Chef> approveChef(@PathVariable String chefId) {
        return ResponseEntity.ok(adminService.approveChef(chefId));
    }

    @DeleteMapping("/chef/{chefId}")
    public ResponseEntity<Chef> rejectChef(@PathVariable String chefId) {
        return ResponseEntity.ok(adminService.rejectChef(chefId));
    }

    @PutMapping("/chef/{chefId}/suspend")
    public ResponseEntity<Chef> suspendChef(@PathVariable String chefId) {
        return ResponseEntity.ok(adminService.suspendChef(chefId));
    }

    @PutMapping("/chef/{chefId}/activate")
    public ResponseEntity<Chef> activateChef(@PathVariable String chefId) {
        return ResponseEntity.ok(adminService.activateChef(chefId));
    }
}