package com.example.springapp.service;

import com.example.springapp.model.Address;
import com.example.springapp.model.User;
import com.example.springapp.repository.AddressRepository;
import com.example.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    // Create address for a specific user
    public ResponseEntity<?> createAddress(Long userId, Address address) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        address.setUser(userOpt.get());

        // If marked as default, unset all other addresses for that user
        if (Boolean.TRUE.equals(address.getIsDefault())) {
            List<Address> userAddresses = addressRepository.findByUserId(userId);
            for (Address addr : userAddresses) {
                addr.setIsDefault(false);
            }
            addressRepository.saveAll(userAddresses);
        }

        Address savedAddress = addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    // Get all addresses of a user
    public ResponseEntity<List<Address>> getUserAddresses(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        if (addresses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(addresses);
    }

    // Update an address
    public ResponseEntity<Address> updateAddress(Long addressId, Address updatedAddress) {
        Optional<Address> existingOpt = addressRepository.findById(addressId);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Address existing = existingOpt.get();
        existing.setStreet(updatedAddress.getStreet());
        existing.setCity(updatedAddress.getCity());
        existing.setState(updatedAddress.getState());
        existing.setPinCode(updatedAddress.getPinCode());
        existing.setIsDefault(updatedAddress.getIsDefault());

        // If updated to default, unset others
        if (Boolean.TRUE.equals(updatedAddress.getIsDefault())) {
            List<Address> userAddresses = addressRepository.findByUserId(existing.getUser().getId());
            for (Address addr : userAddresses) {
                if (!addr.getId().equals(existing.getId())) {
                    addr.setIsDefault(false);
                }
            }
            addressRepository.saveAll(userAddresses);
        }

        return ResponseEntity.ok(addressRepository.save(existing));
    }

    // Delete address
    public ResponseEntity<Void> deleteAddress(Long addressId) {
        if (!addressRepository.existsById(addressId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        addressRepository.deleteById(addressId);
        return ResponseEntity.noContent().build();
    }
}
