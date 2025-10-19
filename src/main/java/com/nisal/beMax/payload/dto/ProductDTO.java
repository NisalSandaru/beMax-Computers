package com.nisal.beMax.payload.dto;

import com.nisal.beMax.domain.ProductCondition;
import com.nisal.beMax.model.Brand;
import com.nisal.beMax.model.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProductDTO {
    private Long id;

    private String name;

    private String sku;

    private ProductCondition productCondition; // NEW, USED

    private Integer stockQty;

    private String description;

    private Double mrp;

    private Double sellingPrice;

    private List<String> images;

    private CategoryDTO category;

    private Long categoryId;

    private Long brandId;

    private BrandDTO brand;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
