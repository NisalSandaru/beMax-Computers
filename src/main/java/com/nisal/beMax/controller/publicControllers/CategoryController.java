package com.nisal.beMax.controller.publicControllers;

import com.nisal.beMax.exceptions.CategoryException;
import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.payload.dto.CategoryDTO;
import com.nisal.beMax.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() throws CategoryException {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
