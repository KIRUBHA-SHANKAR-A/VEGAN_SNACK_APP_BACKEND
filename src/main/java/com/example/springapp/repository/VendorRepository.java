package com.example.springapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.User;
import com.example.springapp.model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor ,Long> {

    Optional<Vendor> findByUser(User user);
    

    @Query("SELECT v FROM Vendor v WHERE v.approvalStatus = 'PENDING'")
    List<Vendor> findPendingVendors();
} 

