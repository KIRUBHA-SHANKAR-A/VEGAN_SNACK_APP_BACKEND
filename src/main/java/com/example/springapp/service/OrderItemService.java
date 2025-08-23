package com.example.springapp.service;

import com.example.springapp.model.OrderItem;
import com.example.springapp.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Get all items
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemRepository.findAll());
    }

    // Get by ID
    public ResponseEntity<OrderItem> getOrderItemById(Long id) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        return orderItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get by OrderId
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(Long orderId) {
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
        return ResponseEntity.ok(items);
    }

    // Create
    public ResponseEntity<OrderItem> createOrderItem(OrderItem orderItem) {
        return ResponseEntity.ok(orderItemRepository.save(orderItem));
    }

    // Update
    public ResponseEntity<OrderItem> updateOrderItem(Long id, OrderItem updatedOrderItem) {
        return orderItemRepository.findById(id).map(existing -> {
            existing.setQuantity(updatedOrderItem.getQuantity());
            existing.setUnitPrice(updatedOrderItem.getUnitPrice());
            existing.setTotalPrice(updatedOrderItem.getTotalPrice());
            existing.setOrder(updatedOrderItem.getOrder());
            existing.setProduct(updatedOrderItem.getProduct());
            return ResponseEntity.ok(orderItemRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete
    public ResponseEntity<Void> deleteOrderItem(Long id) {
        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
