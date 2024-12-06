package com.pocketbazaar.api.controller;


import com.pocketbazaar.api.model.Address;
import com.pocketbazaar.api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Create a new address
    @PostMapping("/add")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        Address createdAddress = addressService.addAddress(address);
        return ResponseEntity.ok(createdAddress);
    }

    // Get all addresses for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddresses(@PathVariable String userId) {
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    // Update an address
    @PutMapping("/update/{addressId}")
    public ResponseEntity<Address> updateAddress(
            @PathVariable String addressId, 
            @RequestBody Address updatedAddress) {
        Address address = addressService.updateAddress(addressId, updatedAddress);
        return ResponseEntity.ok(address);
    }

    // Delete an address
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable String addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok("Address deleted successfully.");
    }
}

