package com.example.springapp.service;

import com.example.springapp.dto.AddVeganSnackRequest;
import com.example.springapp.dto.SnackResponseDTO;
import com.example.springapp.dto.SnackUpdateDTO;
import com.example.springapp.model.Inventory;
import com.example.springapp.model.ProductImages;
import com.example.springapp.model.ProductReview;
import com.example.springapp.model.VeganSnacks;
import com.example.springapp.model.Vendor;
import com.example.springapp.repository.InventoryRepository;
import com.example.springapp.repository.ProductReviewRepository;
import com.example.springapp.repository.VeganSnacksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VeganSnacksService {

    @Autowired
    private VeganSnacksRepository snackRepository;

    @Autowired 
    private ProductReviewRepository reviewRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private InventoryService inventoryService;
    // Create a new snack'
      public ResponseEntity<?> createSnack(AddVeganSnackRequest request) {
        try {
            Optional<Vendor> vendor = vendorService.findById(request.getVendorId());

            if(vendor.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Vendors found");
            }
            VeganSnacks snack = new VeganSnacks();
            
            // Set fields from DTO
            snack.setSnackName(request.getSnackName());
            snack.setSnackType(request.getSnackType());
            snack.setDescription(request.getDescription());
            snack.setIngredients(request.getIngredients());
            snack.setNutritionalInfo(request.getNutritionalInfo());
            snack.setQuantity(request.getQuantity());
            snack.setPrice(request.getPrice());
            snack.setCategory(request.getCategory());
            snack.setExpiryInMonths(request.getExpiryInMonths());
            snack.setSku(request.getSku());
            if (request.getProductImageURL() != null && !request.getProductImageURL().isEmpty()) {
                    try {
                        URL url = new URL(request.getProductImageURL());
                        URLConnection connection = url.openConnection();
                        Long size = (connection.getContentLengthLong() / 1024);

                        ProductImages productImage = ProductImages.builder()
                            .imageUrl(request.getProductImageURL())
                            .altText("img")
                            .fileSize(size)
                            .isActive(true)
                            .veganSnack(snack)
                            .build();

                        snack.setProductImage(productImage);
                    } catch (Exception ex) {
                        System.out.println("⚠️ Failed to fetch image size: " + ex.getMessage());
                        // Optionally: continue without productImage
                    }
                }

            // Set default values for missing fields
            snack.setStatus(VeganSnacks.Status.PENDING_APPROVAL);
            snack.setCreatedDate(LocalDateTime.now());
            snack.setLastModified(LocalDateTime.now());
            snack.setVendor(vendor.get());

            VeganSnacks savedSnack = snackRepository.save(snack);
            
            // Create inventory record
            Inventory inventory = Inventory.builder()
                .veganSnack(savedSnack)
                .currentStock(request.getCurrentStock() != null ? request.getCurrentStock() : 0)
                .reorderPoint(request.getReorderPoint() != null ? request.getReorderPoint() : 10) // Default reorder point
                .maxStock(request.getMaxStock() != null ? request.getMaxStock() : 100) // Default max stock
                .lastUpdated(LocalDateTime.now())
                .build();
            inventoryService.createInventory(inventory);
            
            return ResponseEntity.ok(savedSnack);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<VeganSnacks> getApprovedSnacks() {
        return snackRepository.findApprovedSnacks();
    }

    // Get all snacks
    public ResponseEntity<List<SnackUpdateDTO>> getAllSnacks() {
    List<VeganSnacks> snacks = snackRepository.findAll();
    List<SnackUpdateDTO> snackDTOs = new ArrayList<>();
    
    for (VeganSnacks snack : snacks) {
        SnackUpdateDTO dto = new SnackUpdateDTO();
        dto.setSnackName(snack.getSnackName());
        dto.setSnackType(snack.getSnackType());
        dto.setPrice(snack.getPrice());
        dto.setQuantity(snack.getQuantity());
        dto.setExpiryInMonths(snack.getExpiryInMonths());
        dto.setDescription(snack.getDescription());
        dto.setIngredients(snack.getIngredients());
        dto.setNutritionalInfo(snack.getNutritionalInfo());
        dto.setSku(snack.getSku());
        
        // Add inventory data if available
        if (snack.getInventory() != null) {
            dto.setCurrentStock(snack.getInventory().getCurrentStock());
            dto.setReorderPoint(snack.getInventory().getReorderPoint());
            dto.setMaxStock(snack.getInventory().getMaxStock());
        }
        
        snackDTOs.add(dto);
    }
    
    return ResponseEntity.ok(snackDTOs);
}
    // Get snack by Id
    public ResponseEntity<VeganSnacks> getSnackById(Long id) {
        Optional<VeganSnacks> snack = snackRepository.findById(id);
        return snack.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Update snack
   @Transactional
public ResponseEntity<?> updateSnack(Long id, SnackUpdateDTO dto) {
    Optional<VeganSnacks> snackOpt = snackRepository.findById(id);
    if (snackOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Snack not found with id " + id);
    }

    VeganSnacks snack = snackOpt.get();

    // Update snack details
    snack.setSnackName(dto.getSnackName());
    snack.setSnackType(dto.getSnackType());
    snack.setPrice(dto.getPrice());
    snack.setQuantity(dto.getQuantity());
    snack.setExpiryInMonths(dto.getExpiryInMonths());
    snack.setDescription(dto.getDescription());
    snack.setIngredients(dto.getIngredients());
    snack.setNutritionalInfo(dto.getNutritionalInfo());
    snack.setSku(dto.getSku());
    snack.setLastModified(LocalDateTime.now());

    // Update inventory (fetch it properly)
    Optional<Inventory> inv = inventoryRepository.findByVeganSnackId(snack.getId()); // create if not exists

    Inventory inventory = new Inventory();
    if(!inv.isEmpty())
    {
         inventory = inv.get();
    }
    inventory.setVeganSnack(snack);
    inventory.setCurrentStock(dto.getCurrentStock() != null ? dto.getCurrentStock() : inventory.getCurrentStock());
    inventory.setReorderPoint(dto.getReorderPoint() != null ? dto.getReorderPoint() : inventory.getReorderPoint());
    inventory.setMaxStock(dto.getMaxStock() != null ? dto.getMaxStock() : inventory.getMaxStock());
    inventory.setLastUpdated(LocalDateTime.now());

    inventoryRepository.save(inventory);

    return ResponseEntity.ok(snackRepository.save(snack));
}

    // Delete snack
    public ResponseEntity<Void> deleteSnack(Long id) {
        if (snackRepository.existsById(id)) {
            snackRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get snacks by vendor
    public ResponseEntity<List<SnackResponseDTO>> getSnacksByVendor(Long vendorId) {
    List<VeganSnacks> snacks = snackRepository.findSnackByVendorId(vendorId);

    List<SnackResponseDTO> snackDTOs = snacks.stream().map(v -> {
        SnackResponseDTO dto = new SnackResponseDTO();
        dto.setId(v.getId());
        dto.setSnackName(v.getSnackName());
        dto.setSnackType(v.getSnackType());
        dto.setPrice(v.getPrice());
        dto.setQuantity(v.getQuantity());
        dto.setExpiryInMonths(v.getExpiryInMonths());
        dto.setDescription(v.getDescription());
        dto.setIngredients(v.getIngredients());
        dto.setNutritionalInfo(v.getNutritionalInfo());
        dto.setSku(v.getSku());
        dto.setStatus(v.getStatus().toString());

        // fetch reviews separately
        List<ProductReview> reviews = reviewRepository.findByVeganSnackId(v.getId());
        String combinedReviews = reviews.stream()
                                        .map(r -> r.getRating() + ": " + r.getReviewText())
                                        .collect(Collectors.joining(", "));
        dto.setReviews(combinedReviews.isEmpty() ? "No review" : combinedReviews);

        return dto;
    }).collect(Collectors.toList());

    return ResponseEntity.ok(snackDTOs);
}

   
}
