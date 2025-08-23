package com.example.springapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ProductImages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String altText;
    
    private Long fileSize;
    
    private LocalDateTime uploadDate;
    
    private Boolean isActive;
    
    private String imageUrl;
    
    @OneToOne
    @JoinColumn(name = "product_id", unique = true)
    @JsonBackReference
    private VeganSnacks veganSnack; 

}
