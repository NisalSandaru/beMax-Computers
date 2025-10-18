package com.nisal.beMax.mapper;

import com.nisal.beMax.model.Category;
import com.nisal.beMax.payload.dto.CategoryDTO;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
