package com.example.springapp.dto;

import lombok.Data;

@Data
public class AddVeganSnackRequest {
    private String snackName;
    private String snackType;
    private String description;
    private String ingredients;
    private String nutritionalInfo;
    private Integer quantity;
    private Double price;
    private String category;
    private Integer expiryInMonths;
    private String sku;
    private String productImageURL; // Or whatever represents the image
    private Long vendorId; 
     private Integer currentStock;
    private Integer reorderPoint;
    private Integer maxStock;
}

