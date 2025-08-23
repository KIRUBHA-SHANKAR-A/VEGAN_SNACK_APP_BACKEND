package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.ProductImages;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages ,Long> {

    ProductImages findByVeganSnackId(Long veganSnackId);

    
} 