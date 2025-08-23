package com.example.springapp.controller;

import com.example.springapp.model.ProductReview;
import com.example.springapp.service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ProductReviewController {

    @Autowired
    private ProductReviewService reviewService;

    @PostMapping
    public ResponseEntity<ProductReview> createReview(@RequestBody ProductReview review) {
        return reviewService.createReview(review);
    }

    @GetMapping
    public ResponseEntity<List<ProductReview>> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReview> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductReview> updateReview(@PathVariable Long id,
                                                      @RequestBody ProductReview updated) {
        return reviewService.updateReview(id, updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        return reviewService.deleteReview(id);
    }

    @GetMapping("/snack/{veganSnackId}")
    public ResponseEntity<List<ProductReview>> getReviewsByVeganSnack(@PathVariable Long veganSnackId) {
        return reviewService.getReviewsByVeganSnack(veganSnackId);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ProductReview>> getReviewsByCustomer(@PathVariable Long customerId) {
        return reviewService.getReviewsByCustomer(customerId);
    }
}
