package com.example.springapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private BigDecimal amount;
    
    private String paymentMethod;  // e.g., CREDIT_CARD, PAYPAL, UPI, etc.
    
    private String paymentStatus;  // e.g., PENDING, COMPLETED, FAILED
    
    private LocalDateTime paymentDate;
    
    private String transactionId;  // external payment gateway transaction reference
    
    // Each payment belongs to exactly one order
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", unique = true)
    @JsonBackReference
    private Order order;
}
