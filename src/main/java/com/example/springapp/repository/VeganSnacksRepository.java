package com.example.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.VeganSnacks;

@Repository
public interface VeganSnacksRepository extends JpaRepository<VeganSnacks, Long> {
    
   @Query("SELECT v FROM VeganSnacks v WHERE v.vendor.id = :vendorId")
    List<VeganSnacks> findSnackByVendorId(@Param("vendorId") Long vendorId);

    @Query("SELECT vs FROM VeganSnacks vs WHERE vs.status = 'PENDING_APPROVAL'")
    List<VeganSnacks> findPendingApprovalSnacks();
    
    @Query("SELECT vs FROM VeganSnacks vs WHERE vs.status = 'APPROVED'")
    List<VeganSnacks> findApprovedSnacks();
}