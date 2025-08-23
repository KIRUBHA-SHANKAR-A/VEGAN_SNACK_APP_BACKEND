package com.example.springapp.dto;

import lombok.Data;

@Data
public class VendorRegisterRequest {
    
    private UserRegisterRequest userRegisterRequest;
    
    private String businessName;

    private String businessLicenseNumber;

    private String taxId;

    private String businessAddress;
    
    private String businessDescription;
    
}


