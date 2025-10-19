package com.nisal.beMax.model;

import com.nisal.beMax.domain.ProductCondition;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String sku;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCondition productCondition; // NEW, USED

    @Min(0)
    private Integer stockQty;

    private String description;

    private Double mrp;

    @Column(nullable = false)
    @Min(1)
    private Double sellingPrice;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Brand brand;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

}
