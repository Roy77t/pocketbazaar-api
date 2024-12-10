// package com.pocketbazaar.api.controller;


// import com.pocketbazaar.api.model.Address;
// import com.pocketbazaar.api.service.AddressService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/address")
// public class AddressController {

//     @Autowired
//     private AddressService addressService;

//     // Create a new address
//     @PostMapping("/add")
//     public ResponseEntity<Address> addAddress(@RequestBody Address address) {
//         Address createdAddress = addressService.addAddress(address);
//         return ResponseEntity.ok(createdAddress);
//     }

//     // Get all addresses for a user
//     @GetMapping("/user/{userId}")
//     public ResponseEntity<List<Address>> getAddresses(@PathVariable String userId) {
//         List<Address> addresses = addressService.getAddressesByUserId(userId);
//         return ResponseEntity.ok(addresses);
//     }

//     // Update an address
//     @PutMapping("/update/{addressId}")
//     public ResponseEntity<Address> updateAddress(
//             @PathVariable String addressId, 
//             @RequestBody Address updatedAddress) {
//         Address address = addressService.updateAddress(addressId, updatedAddress);
//         return ResponseEntity.ok(address);
//     }

//     // Delete an address
//     @DeleteMapping("/delete/{addressId}")
//     public ResponseEntity<String> deleteAddress(@PathVariable String addressId) {
//         addressService.deleteAddress(addressId);
//         return ResponseEntity.ok("Address deleted successfully.");
//     }
// }

package com.pocketbazaar.api.controller;

import com.pocketbazaar.api.model.Address;
import com.pocketbazaar.api.service.AddressService;
import com.pocketbazaar.api.util.CurrentUserSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    
    @Autowired
    private AddressService addressService;

    // 1. Create a new address for the current user
    @PostMapping("/add")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        
        UserDetails userDetails = CurrentUserSession.getCurrentUserDetails();
        logger.info("Received userId in Controller: {}", userDetails);
        Address createdAddress = addressService.addAddressForUser(address, userDetails.getUsername());
        return ResponseEntity.ok(createdAddress);
    }

    // 2. Get all addresses for the current user
    @GetMapping("/my-addresses")
    public ResponseEntity<List<Address>> getUserAddresses() {
        UserDetails userDetails = CurrentUserSession.getCurrentUserDetails();
        List<Address> addresses = addressService.getAddressesByUserEmail(userDetails.getUsername());
        return ResponseEntity.ok(addresses);
    }

    // 3. Update an address (only if it belongs to the current user)
    @PutMapping("/update/{addressId}")
    public ResponseEntity<Address> updateAddress(
            @PathVariable String addressId, @RequestBody Address updatedAddress) {
        UserDetails userDetails = CurrentUserSession.getCurrentUserDetails();
        Address address = addressService.updateAddress(addressId, updatedAddress, userDetails.getUsername());
        return ResponseEntity.ok(address);
    }

    // 4. Delete an address (only if it belongs to the current user)
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable String addressId) {
        UserDetails userDetails = CurrentUserSession.getCurrentUserDetails();
        addressService.deleteAddress(addressId, userDetails.getUsername());
        return ResponseEntity.ok("Address deleted successfully.");
    }
}
