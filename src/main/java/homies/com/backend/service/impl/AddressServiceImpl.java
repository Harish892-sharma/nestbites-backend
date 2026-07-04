package homies.com.backend.service.impl;

import homies.com.backend.model.Address;
import homies.com.backend.repository.AddressRepository;
import homies.com.backend.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address saveAddress(Address address) {

        return addressRepository.save(address);
    }

    @Override
    public List<Address> getUserAddresses(String userId) {

        return addressRepository.findByUserId(userId);
    }

    @Override
    public void deleteAddress(String id) {

        addressRepository.deleteById(id);
    }
}