package com.nisal.beMax.controller.adminControllers;

import com.nisal.beMax.exceptions.BrandException;
import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.payload.dto.BrandDTO;
import com.nisal.beMax.payload.response.ApiResponse;
import com.nisal.beMax.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/brands")
@RequiredArgsConstructor
public class AdminBrandController {
    private final BrandService brandService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BrandDTO> createCustomer(@RequestBody BrandDTO brandDTO) throws BrandException, UserException {
        return ResponseEntity.ok(
                brandService.createBrand(brandDTO)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateCategory(
            @RequestBody BrandDTO categoryDTO,
            @PathVariable Long id) throws BrandException, UserException {
        return ResponseEntity.ok(
                brandService.updateBrand(id, categoryDTO)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Long id) throws Exception {

        brandService.deleteBrand(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Brand deleted successfully");
        return ResponseEntity.ok(
                apiResponse
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAll() throws Exception {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

}
