package com.example.springapp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.dto.CreateProductManagerDTO;
import com.example.springapp.model.User;
import com.example.springapp.model.VeganSnacks;
import com.example.springapp.model.Vendor;
import com.example.springapp.repository.UserRepository;
import com.example.springapp.repository.VeganSnacksRepository;
import com.example.springapp.repository.VendorRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VeganSnacksRepository veganSnacksRepository;
    


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // configure in SecurityConfig
    @PostMapping("/create-product-manager")
    public User createProductManager(@RequestBody CreateProductManagerDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setRole(User.Role.PRODUCT_MANAGER);
        return userRepository.save(user);
    }
    
    // Get all vendors with PENDING status
    @GetMapping("/vendors")
    public List<Vendor> getPendingVendors() {
        return vendorRepository.findPendingVendors();
    }
    
    @GetMapping("/snacks")
    public List<VeganSnacks> findPendingApprovalSnacks() {
        return veganSnacksRepository.findPendingApprovalSnacks();
    }

    @PutMapping("/vendor/approve/{id}")
    public ResponseEntity<String> approveVendor(@PathVariable Long id) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);

        if (optionalVendor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Vendor vendor = optionalVendor.get();
        vendor.setApprovalStatus(Vendor.ApprovalStatus.APPROVED);
        vendor.setApprovalDate(LocalDateTime.now());

        vendorRepository.save(vendor);

        return ResponseEntity.ok("Vendor approved successfully!");
    }

    @PutMapping("/vendor/reject/{id}")
    public ResponseEntity<String> rejectVendor(@PathVariable Long id) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);

        if (optionalVendor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Vendor vendor = optionalVendor.get();
        vendor.setApprovalStatus(Vendor.ApprovalStatus.REJECTED);
        vendor.setApprovalDate(LocalDateTime.now());

        vendorRepository.save(vendor);

        return ResponseEntity.ok("Vendor rejected!");
    }

    @PutMapping("/snack/approve/{id}")
    public ResponseEntity<String> approveSnack(@PathVariable Long id) {
        Optional<VeganSnacks> optionalSnack = veganSnacksRepository.findById(id);

        if (optionalSnack.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        VeganSnacks snack = optionalSnack.get();
        snack.setStatus(VeganSnacks.Status.APPROVED);
        snack.setApprovalDate(LocalDateTime.now());

        veganSnacksRepository.save(snack);

        return ResponseEntity.ok("Snack approved successfully!");
    }

    @PutMapping("/snack/reject/{id}")
    public ResponseEntity<String> rejectSnack(@PathVariable Long id) {
        Optional<VeganSnacks> optionalSnack = veganSnacksRepository.findById(id);

        if (optionalSnack.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        VeganSnacks snack = optionalSnack.get();
        snack.setStatus(VeganSnacks.Status.REJECTED);
        snack.setApprovalDate(LocalDateTime.now());

        veganSnacksRepository.save(snack);

        return ResponseEntity.ok("Snack rejected!");
    }

}
    


