package homies.com.backend.service;

import homies.com.backend.model.Address;
import java.util.List;

public interface AddressService {

    Address saveAddress(Address address);

    List<Address> getUserAddresses(String userId);

    Address getAddressById(String id);

    void deleteAddress(String id);
}