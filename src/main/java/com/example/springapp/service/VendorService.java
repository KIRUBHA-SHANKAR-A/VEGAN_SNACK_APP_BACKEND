package com.example.springapp.service;

import com.example.springapp.model.Vendor;
import com.example.springapp.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    // Create a new vendor
    public ResponseEntity<Vendor> createVendor(Vendor vendor) {
        Vendor saved = vendorRepository.save(vendor);
        return ResponseEntity.status(201).body(saved);
    }

    // Get all vendors
    public ResponseEntity<List<Vendor>> getAllVendors() {
        return ResponseEntity.ok(vendorRepository.findAll());
    }

    // Get vendor by id
    public ResponseEntity<Vendor> getVendorById(Long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        return vendor.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // Update vendor
    public ResponseEntity<Vendor> updateVendor(Long id, Vendor updated) {
        return vendorRepository.findById(id).map(existing -> {
            existing.setBusinessName(updated.getBusinessName());
            existing.setBusinessLicenseNumber(updated.getBusinessLicenseNumber());
            existing.setTaxId(updated.getTaxId());
            existing.setBusinessAddress(updated.getBusinessAddress());
            existing.setBusinessPhone(updated.getBusinessPhone());
            existing.setBusinessEmail(updated.getBusinessEmail());
            existing.setEstablishedYear(updated.getEstablishedYear());
            existing.setBusinessDescription(updated.getBusinessDescription());
            existing.setApprovalStatus(updated.getApprovalStatus());
            existing.setApprovalDate(updated.getApprovalDate());
            existing.setApprovedBy(updated.getApprovedBy());
            Vendor saved = vendorRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete vendor
    public ResponseEntity<Void> deleteVendor(Long id) {
        if (vendorRepository.existsById(id)) {
            vendorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public Optional<Vendor> findById(Long vendorId) {
        return vendorRepository.findById(vendorId);
    }
}
