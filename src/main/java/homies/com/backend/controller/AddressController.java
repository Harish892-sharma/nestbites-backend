package homies.com.backend.controller;

import homies.com.backend.model.Address;
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

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address address) {

        return ResponseEntity.ok(addressService.saveAddress(address));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> getAll(@PathVariable String userId) {

        return ResponseEntity.ok(addressService.getUserAddresses(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {

        addressService.deleteAddress(id);

        return ResponseEntity.ok("Address Deleted Successfully");
    }
}