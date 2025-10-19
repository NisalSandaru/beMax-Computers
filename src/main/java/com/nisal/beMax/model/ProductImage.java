package com.nisal.beMax.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imagePath; // file path or URL

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
