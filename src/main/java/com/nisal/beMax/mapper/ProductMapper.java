package com.nisal.beMax.mapper;

import com.nisal.beMax.domain.ProductCondition;
import com.nisal.beMax.model.Brand;
import com.nisal.beMax.model.Category;
import com.nisal.beMax.model.Product;
import com.nisal.beMax.payload.dto.ProductDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .productCondition(product.getProductCondition())
                .stockQty(product.getStockQty())
                .description(product.getDescription())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .images(product.getImages() != null
                        ? product.getImages().stream()
                        .map(img -> img.getImagePath())
                        .collect(Collectors.toList())
                        : null)
                .category(product.getCategory() != null ? CategoryMapper.toDTO(product.getCategory()) : null)
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .brand(product.getBrand() != null ? BrandMapper.toDTO(product.getBrand()) : null)
                .brandId(product.getBrand() != null ? product.getBrand().getId() : null)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static Product toEntity(ProductDTO dto, Category category, Brand brand) {
        return Product.builder()
                .name(dto.getName())
                .sku(dto.getSku())
                .productCondition(dto.getProductCondition())
                .stockQty(dto.getStockQty())
                .description(dto.getDescription())
                .mrp(dto.getMrp())
                .sellingPrice(dto.getSellingPrice())
                .category(category)
                .brand(brand)
                .images(new ArrayList<>()) // ✅ Ensure list initialized
                .build();
    }
}
