package com.nisal.beMax.mapper;

import com.nisal.beMax.model.Brand;
import com.nisal.beMax.payload.dto.BrandDTO;

public class BrandMapper {
    public static BrandDTO toDTO(Brand brand) {
        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
