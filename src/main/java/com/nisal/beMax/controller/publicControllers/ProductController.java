package com.nisal.beMax.controller.publicControllers;

import com.nisal.beMax.payload.dto.ProductDTO;
import com.nisal.beMax.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() throws Exception {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) throws Exception {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/byCategory/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long id) throws Exception {
        List<ProductDTO> products = productService.getProductsByCategory(id);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/byBrand/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByBrand(@PathVariable Long id) throws Exception {
        List<ProductDTO> products = productService.getProductsByBrand(id);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/byCatBrand/{catid}/{brandid}")
    public ResponseEntity<List<ProductDTO>> getProductsByCatBrand(@PathVariable Long catid,
                                                                  @PathVariable Long brandid) throws Exception {
        List<ProductDTO> products = productService.getProductsByCategoryAndBrand(catid, brandid);
        return ResponseEntity.ok(products);
    }

}
