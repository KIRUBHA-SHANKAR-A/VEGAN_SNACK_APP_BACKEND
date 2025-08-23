package com.example.springapp.service;

import com.example.springapp.model.ProductReview;
import com.example.springapp.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewService {

    @Autowired
    private ProductReviewRepository reviewRepository;

    public ResponseEntity<ProductReview> createReview(ProductReview review) {
        ProductReview saved = reviewRepository.save(review);
        return ResponseEntity.status(201).body(saved);
    }

    public ResponseEntity<List<ProductReview>> getAllReviews() {
        return ResponseEntity.ok(reviewRepository.findAll());
    }

    public ResponseEntity<ProductReview> getReviewById(Long id) {
        Optional<ProductReview> review = reviewRepository.findById(id);
        return review.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<ProductReview> updateReview(Long id, ProductReview updated) {
        return reviewRepository.findById(id).map(existing -> {
            existing.setRating(updated.getRating());
            existing.setReviewText(updated.getReviewText());
            ProductReview saved = reviewRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteReview(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<ProductReview>> getReviewsByVeganSnack(Long veganSnackId) {
        List<ProductReview> reviews = reviewRepository.findByVeganSnackId(veganSnackId);
        return ResponseEntity.ok(reviews);
    }

    public ResponseEntity<List<ProductReview>> getReviewsByCustomer(Long customerId) {
        List<ProductReview> reviews = reviewRepository.findByCustomerId(customerId);
        return ResponseEntity.ok(reviews);
    }
}
