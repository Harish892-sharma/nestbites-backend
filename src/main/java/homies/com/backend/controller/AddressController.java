package homies.com.backend.controller;

import homies.com.backend.model.Address;
import homies.com.backend.security.CurrentUserUtil;
import homies.com.backend.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address address) {
        // Always attach to the logged-in user, never trust a client-sent userId
        address.setUserId(currentUserUtil.getCurrentUserId());
        return ResponseEntity.ok(addressService.saveAddress(address));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> getAll(@PathVariable String userId) {
        currentUserUtil.requireSelfOrAdmin(userId);
        return ResponseEntity.ok(addressService.getUserAddresses(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        currentUserUtil.requireSelfOrAdmin(addressService.getAddressById(id).getUserId());
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Address Deleted Successfully");
    }
}
