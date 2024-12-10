// package com.pocketbazaar.api.service;


// import com.pocketbazaar.api.model.Address;
// import com.pocketbazaar.api.repository.AddressRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class AddressService {

//     @Autowired
//     private AddressRepository addressRepository;

//     public Address addAddress(Address address) {
//         return addressRepository.save(address);
//     }

//     public List<Address> getAddressesByUserId(String userId) {
//         return addressRepository.findByUserId(userId);
//     }

//     public Address updateAddress(String addressId, Address updatedAddress) {
//         Address existingAddress = addressRepository.findById(addressId)
//                 .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));

//         // Update the address details
//         existingAddress.setName(updatedAddress.getName());
//         existingAddress.setMobile(updatedAddress.getMobile());
//         existingAddress.setPincode(updatedAddress.getPincode());
//         existingAddress.setDistrict(updatedAddress.getDistrict());
//         existingAddress.setAddress(updatedAddress.getAddress());
//         existingAddress.setLocality(updatedAddress.getLocality());
//         existingAddress.setState(updatedAddress.getState());

//         return addressRepository.save(existingAddress);
//     }

//     public void deleteAddress(String addressId) {
//         addressRepository.deleteById(addressId);
//     }
// }



package com.pocketbazaar.api.service;

import com.pocketbazaar.api.model.Address;
import com.pocketbazaar.api.repository.AddressRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;

    // 1. Add address for the current user
    public Address addAddressForUser(Address address, String userId) {
        logger.info("Adding address for userId: {}", userId);
        address.setUserId(userId); // Associate the address with the user
        return addressRepository.save(address);
    }

    // 2. Get all addresses for the current user
    public List<Address> getAddressesByUserEmail(String userId) {
        return addressRepository.findByUserId(userId);
    }

    // 3. Update an address (only if it belongs to the user)
    public Address updateAddress(String addressId, Address updatedAddress, String userId) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));

        if (!existingAddress.getUserID().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this address.");
        }

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

    // 4. Delete an address (only if it belongs to the user)
    public void deleteAddress(String addressId, String userId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));

        if (!address.getUserID().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this address.");
        }

        addressRepository.delete(address);
    }
}
