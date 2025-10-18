package com.nisal.beMax.controller.publicControllers;

import com.nisal.beMax.payload.dto.BrandDTO;
import com.nisal.beMax.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAll() throws Exception {
        return ResponseEntity.ok(brandService.getAllBrands());
    }
}
