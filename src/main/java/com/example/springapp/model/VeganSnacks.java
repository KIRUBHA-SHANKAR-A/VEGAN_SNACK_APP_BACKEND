package com.example.springapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "VeganSnacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class VeganSnacks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String snackName;
    private String snackType;
    private String description;
    private String ingredients;
    private String nutritionalInfo;
    private Integer quantity;
    private Double price;
    private Integer expiryInMonths;
    private String sku;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String category;

    private LocalDateTime createdDate;
    private LocalDateTime lastModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedByUser;

    private LocalDateTime approvalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @JsonBackReference("vendor-snacks")
    private Vendor vendor;


    @OneToOne(mappedBy = "veganSnack", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Inventory inventory;

    @OneToOne(mappedBy = "veganSnack", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private ProductImages productImage; // INVERSE side

    @OneToMany(mappedBy = "veganSnack", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("snack-reviews")
    private List<ProductReview> productReviews;

    public enum Status {
        PENDING_APPROVAL,
        APPROVED,
        REJECTED
    }
}
