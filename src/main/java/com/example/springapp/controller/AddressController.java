package com.example.springapp.controller;

import com.example.springapp.model.Address;
import com.example.springapp.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Create address for a specific user
    @PostMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'VENDOR','ADMIN')")
    public ResponseEntity<?> createAddress(@PathVariable Long userId, @RequestBody Address address) {
        return addressService.createAddress(userId, address);
    }
    
    // Get all addresses of a user
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'VENDOR','ADMIN', 'PRODUCT_MANAGER')")
    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable Long userId) {
        return addressService.getUserAddresses(userId);
    }

    // Update an address
    @PutMapping("/{addressId}")
    @PreAuthorize("hasAnyRole('USER', 'VENDOR','ADMIN',PRODUCT_MANAGER')")
    public ResponseEntity<Address> updateAddress(@PathVariable Long addressId, @RequestBody Address updatedAddress) {
        return addressService.updateAddress(addressId, updatedAddress);
    }

    // Delete an address
    @DeleteMapping("/{addressId}")
    @PreAuthorize("hasAnyRole('USER', 'VENDOR','ADMIN')")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        return addressService.deleteAddress(addressId);
    }
}
