package homies.com.backend.controller.thali;

import homies.com.backend.model.thali.Thali;
import homies.com.backend.security.CurrentUserUtil;
import homies.com.backend.service.thali.ThaliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/thali")
public class ThaliController {

    @Autowired
    private ThaliService thaliService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @PostMapping
    public ResponseEntity<Thali> addThali(@RequestBody Thali thali) {

        thali.setChefId(currentUserUtil.getCurrentChefId());

        return ResponseEntity.ok(
                thaliService.addThali(thali)
        );

    }

    @GetMapping("/all")
    public ResponseEntity<List<Thali>> getAllThalis() {

        return ResponseEntity.ok(
                thaliService.getAllThalis()
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<Thali> getThaliById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                thaliService.getThaliById(id)
        );

    }

    @GetMapping("/chef/{chefId}")
    public ResponseEntity<List<Thali>> getChefThalis(
            @PathVariable String chefId) {

        return ResponseEntity.ok(
                thaliService.getChefThalis(chefId)
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteThali(
            @PathVariable String id) {

        currentUserUtil.requireChefOwnerOrAdmin(
                thaliService.getThaliById(id).getChefId()
        );

        thaliService.deleteThali(id);

        return ResponseEntity.ok("Thali Deleted Successfully");

    }

}
