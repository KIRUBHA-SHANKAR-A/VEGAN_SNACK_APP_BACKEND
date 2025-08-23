package com.example.springapp.controller;

import com.example.springapp.model.Inventory;
import com.example.springapp.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Create Inventory
    // @PostMapping
    // @PreAuthorize("hasRole('VENDOR')")
    // public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
    //     return inventoryService.createInventory(inventory);
    // }

    // Get All Inventories
    @GetMapping
    @PreAuthorize("hasRole('ADMIN', 'PRODUCT_MANAGER')")
    public ResponseEntity<List<Inventory>> getAllInventories() {
        return inventoryService.getAllInventories();
    }

    // Get Inventory by Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN', 'PRODUCT_MANAGER')")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    // Update Inventory
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id,
                                                     @RequestBody Inventory inventory) {
        return inventoryService.updateInventory(id, inventory);
    }

    // Delete Inventory
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        return inventoryService.deleteInventory(id);
    }

    // Get Inventory by Product (VeganSnack) Id
    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN', 'PRODUCT_MANAGER')")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        return inventoryService.getInventoryByProductId(productId);
    }
}
