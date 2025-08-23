package com.example.springapp.service;

import com.example.springapp.model.Inventory;
import com.example.springapp.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // Create Inventory
    public Inventory createInventory(Inventory inventory) {
        
        return inventoryRepository.save(inventory);
    }
    // Get All Inventories
    public ResponseEntity<List<Inventory>> getAllInventories() {
        return ResponseEntity.ok(inventoryRepository.findAll());
    }

    // Get Inventory by Id
    public ResponseEntity<Inventory> getInventoryById(Long id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        return inventory.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    // Update Inventory
    public ResponseEntity<Inventory> updateInventory(Long id, Inventory updatedInventory) {
        Optional<Inventory> existing = inventoryRepository.findById(id);
        if (existing.isPresent()) {
            Inventory inv = existing.get();
            inv.setCurrentStock(updatedInventory.getCurrentStock());
            inv.setReorderPoint(updatedInventory.getReorderPoint());
            inv.setMaxStock(updatedInventory.getMaxStock());
            inv.setLastUpdated(updatedInventory.getLastUpdated());
            Inventory saved = inventoryRepository.save(inv);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete Inventory
    public ResponseEntity<Void> deleteInventory(Long id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Custom: Get Inventory by Product (VeganSnack) Id
    public ResponseEntity<Inventory> getInventoryByProductId(Long productId) {
        Optional<Inventory> inventories = inventoryRepository.findByVeganSnackId(productId);
        return ResponseEntity.ok(inventories.get());
    }
}
