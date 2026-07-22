package homies.com.backend.controller.tiffin;

import homies.com.backend.model.tiffin.Tiffin;
import homies.com.backend.security.CurrentUserUtil;
import homies.com.backend.service.tiffin.TiffinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tiffin")
public class TiffinController {

    @Autowired
    private TiffinService tiffinService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @PostMapping
    public ResponseEntity<Tiffin> addTiffin(@RequestBody Tiffin tiffin) {

        tiffin.setChefId(currentUserUtil.getCurrentChefId());

        return ResponseEntity.ok(tiffinService.addTiffin(tiffin));

    }

    @GetMapping("/all")
    public ResponseEntity<List<Tiffin>> getAllTiffins() {

        return ResponseEntity.ok(tiffinService.getAllTiffins());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Tiffin> getTiffin(@PathVariable String id) {

        return ResponseEntity.ok(tiffinService.getTiffinById(id));

    }

    @GetMapping("/chef/{chefId}")
    public ResponseEntity<List<Tiffin>> getChefTiffins(@PathVariable String chefId) {

        return ResponseEntity.ok(tiffinService.getChefTiffins(chefId));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTiffin(@PathVariable String id) {

        currentUserUtil.requireChefOwnerOrAdmin(
                tiffinService.getTiffinById(id).getChefId()
        );

        tiffinService.deleteTiffin(id);

        return ResponseEntity.ok("Tiffin Deleted Successfully");

    }

}
