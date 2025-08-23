package com.example.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.ProductReview;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview ,Long> {

    List<ProductReview> findByVeganSnackId(Long veganSnackId);

    List<ProductReview> findByCustomerId(Long customerId);

    @Query("SELECT pr FROM ProductReview pr WHERE pr.veganSnack.id = :snackId")
    List<ProductReview> findReviewsBySnackId(@Param("snackId") Long snackId);

     List<ProductReview> findByVeganSnackId(Integer veganSnackId);
    
    List<ProductReview> findByCustomerId(Integer customerId);
    
    @Query("SELECT AVG(pr.rating) FROM ProductReview pr WHERE pr.veganSnack.id = :veganSnackId")
    Double findAverageRatingByVeganSnackId(@Param("veganSnackId") Integer veganSnackId);
    
    @Query("SELECT COUNT(pr) FROM ProductReview pr WHERE pr.veganSnack.id = :veganSnackId")
    Integer countByVeganSnackId(@Param("veganSnackId") Integer veganSnackId);
    
    boolean existsByCustomerIdAndVeganSnackId(Integer customerId, Integer veganSnackId);
    
} 