package com.nisal.beMax.controller.adminControllers;

import com.nisal.beMax.exceptions.BrandException;
import com.nisal.beMax.exceptions.CategoryException;
import com.nisal.beMax.exceptions.ProductException;
import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.model.User;
import com.nisal.beMax.payload.dto.ProductDTO;
import com.nisal.beMax.payload.response.ApiResponse;
import com.nisal.beMax.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ProductDTO> createProduct(
            @RequestPart("product") ProductDTO productDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws IOException, CategoryException, BrandException, UserException, ProductException {
        ProductDTO created = productService.createProduct(productDTO, images);
        return ResponseEntity.ok(created);
    }

    @PutMapping(value = "{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") ProductDTO productDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws Exception {
        ProductDTO updated = productService.updateProduct(id, productDTO, images);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) throws Exception {

        productService.deleteProduct(
                id
        );

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Product deleted successfully");

        return ResponseEntity.ok(
                apiResponse
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() throws Exception {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) throws Exception {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/byCategory/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long id) throws Exception {
        List<ProductDTO> products = productService.getProductsByCategory(id);
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/byBrand/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByBrand(@PathVariable Long id) throws Exception {
        List<ProductDTO> products = productService.getProductsByBrand(id);
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/byCatBrand/{catid}/{brandid}")
    public ResponseEntity<List<ProductDTO>> getProductsByCatBrand(@PathVariable Long catid,
                                                                  @PathVariable Long brandid) throws Exception {
        List<ProductDTO> products = productService.getProductsByCategoryAndBrand(catid, brandid);
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/byKeyword/{keyword}")
    public ResponseEntity<List<ProductDTO>> getProductsByKeyword(@PathVariable String keyword) throws Exception {
        List<ProductDTO> productDTOS = productService.searchByKeyword(keyword);
        return ResponseEntity.ok(productDTOS);
    }

}
