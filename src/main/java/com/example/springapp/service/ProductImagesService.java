package com.example.springapp.service;

import com.example.springapp.model.ProductImages;
import com.example.springapp.repository.ProductImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductImagesService {

    @Autowired
    private ProductImagesRepository productImagesRepository;

    // Create / Save ProductImage
    public ResponseEntity<ProductImages> createProductImage(ProductImages image) {
        image.setUploadDate(LocalDateTime.now());
        ProductImages saved = productImagesRepository.save(image);
        return ResponseEntity.status(201).body(saved);
    }

    // Get all images
    public ResponseEntity<List<ProductImages>> getAllImages() {
        return ResponseEntity.ok(productImagesRepository.findAll());
    }

    // Get image by ID
    public ResponseEntity<ProductImages> getImageById(Long id) {
        Optional<ProductImages> image = productImagesRepository.findById(id);
        return image.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Update image
    public ResponseEntity<ProductImages> updateImage(Long id, ProductImages updated) {
        return productImagesRepository.findById(id).map(existing -> {
            existing.setAltText(updated.getAltText());
            existing.setFileSize(updated.getFileSize());
            existing.setIsActive(updated.getIsActive());
            existing.setImageUrl(updated.getImageUrl());
            existing.setVeganSnack(updated.getVeganSnack());
            ProductImages saved = productImagesRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete image
    public ResponseEntity<Void> deleteImage(Long id) {
        if (productImagesRepository.existsById(id)) {
            productImagesRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get image by VeganSnack ID (one-to-one)
    public ResponseEntity<ProductImages> getImageByVeganSnackId(Long veganSnackId) {
        ProductImages image = productImagesRepository.findByVeganSnackId(veganSnackId);
        if (image != null) return ResponseEntity.ok(image);
        return ResponseEntity.notFound().build();
    }
}
