package com.example.springapp.controller;

import com.example.springapp.model.ProductImages;
import com.example.springapp.service.ProductImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-images")
public class ProductImagesController {

    @Autowired
    private ProductImagesService productImagesService;

    @PostMapping
    public ResponseEntity<ProductImages> createImage(@RequestBody ProductImages image) {
        return productImagesService.createProductImage(image);
    }

    @GetMapping
    public ResponseEntity<List<ProductImages>> getAllImages() {
        return productImagesService.getAllImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductImages> getImageById(@PathVariable Long id) {
        return productImagesService.getImageById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductImages> updateImage(@PathVariable Long id,
                                                     @RequestBody ProductImages updated) {
        return productImagesService.updateImage(id, updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        return productImagesService.deleteImage(id);
    }

    @GetMapping("/snack/{veganSnackId}")
    public ResponseEntity<ProductImages> getImageByVeganSnackId(@PathVariable Long veganSnackId) {
        return productImagesService.getImageByVeganSnackId(veganSnackId);
    }
}
