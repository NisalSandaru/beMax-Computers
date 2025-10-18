package com.nisal.beMax.service;

import com.nisal.beMax.exceptions.CategoryException;
import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.payload.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO dto) throws CategoryException, UserException;
    CategoryDTO updateCategory(Long id,CategoryDTO dto) throws CategoryException, UserException;
    List<CategoryDTO> getAllCategories() throws CategoryException;
    void deleteCategory(Long id) throws CategoryException, UserException;

}
