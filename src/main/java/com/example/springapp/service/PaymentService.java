package com.example.springapp.service;

import com.example.springapp.model.Payment;
import com.example.springapp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Create Payment
    public ResponseEntity<Payment> createPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        Payment saved = paymentRepository.save(payment);
        return ResponseEntity.status(201).body(saved);
    }

    // Get All Payments
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentRepository.findAll());
    }

    // Get Payment by Id
    public ResponseEntity<Payment> getPaymentById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // Update Payment
    public ResponseEntity<Payment> updatePayment(Long id, Payment updatedPayment) {
        Optional<Payment> existing = paymentRepository.findById(id);
        if (existing.isPresent()) {
            Payment payment = existing.get();
            payment.setAmount(updatedPayment.getAmount());
            payment.setPaymentMethod(updatedPayment.getPaymentMethod());
            payment.setPaymentStatus(updatedPayment.getPaymentStatus());
            payment.setTransactionId(updatedPayment.getTransactionId());
            Payment saved = paymentRepository.save(payment);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete Payment
    public ResponseEntity<Void> deletePayment(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get Payment by Order Id
    public ResponseEntity<Payment> getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        }
        return ResponseEntity.notFound().build();
    }
}
