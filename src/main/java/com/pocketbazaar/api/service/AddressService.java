package com.pocketbazaar.api.service;


import com.pocketbazaar.api.model.Address;
import com.pocketbazaar.api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> getAddressesByUserId(String userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address updateAddress(String addressId, Address updatedAddress) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));

        // Update the address details
        existingAddress.setName(updatedAddress.getName());
        existingAddress.setMobile(updatedAddress.getMobile());
        existingAddress.setPincode(updatedAddress.getPincode());
        existingAddress.setDistrict(updatedAddress.getDistrict());
        existingAddress.setAddress(updatedAddress.getAddress());
        existingAddress.setLocality(updatedAddress.getLocality());
        existingAddress.setState(updatedAddress.getState());

        return addressRepository.save(existingAddress);
    }

    public void deleteAddress(String addressId) {
        addressRepository.deleteById(addressId);
    }
}

