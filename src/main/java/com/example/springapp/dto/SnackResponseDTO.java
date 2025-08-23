package com.example.springapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  
@AllArgsConstructor
@NoArgsConstructor 
public class SnackResponseDTO {
    private Long id;
    private String snackName;
    private String snackType;
    private Double price;
    private Integer quantity;
    private Integer expiryInMonths;
    private String description;
    private String ingredients;
    private String nutritionalInfo;
    private String sku;
    private Integer currentStock;
    private Integer reorderPoint;
    private Integer maxStock;
    private String status;
    private String reviews;
}


