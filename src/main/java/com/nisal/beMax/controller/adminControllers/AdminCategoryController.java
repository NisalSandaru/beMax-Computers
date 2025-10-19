package com.nisal.beMax.controller.adminControllers;

import com.nisal.beMax.exceptions.CategoryException;
import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.payload.dto.CategoryDTO;
import com.nisal.beMax.payload.response.ApiResponse;
import com.nisal.beMax.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) throws CategoryException, UserException {
        return ResponseEntity.ok(
                categoryService.createCategory(categoryDTO)
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() throws CategoryException {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @RequestBody CategoryDTO categoryDTO,
            @PathVariable Long id) throws CategoryException, UserException {
        return ResponseEntity.ok(
                categoryService.updateCategory(id, categoryDTO)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Long id) throws Exception {

        categoryService.deleteCategory(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Category deleted successfully");
        return ResponseEntity.ok(
                apiResponse
        );
    }

}
