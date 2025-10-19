package com.nisal.beMax.service;

import com.nisal.beMax.exceptions.BrandException;
import com.nisal.beMax.exceptions.CategoryException;
import com.nisal.beMax.exceptions.ProductException;
import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.model.User;
import com.nisal.beMax.payload.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO, List<MultipartFile> images) throws CategoryException, BrandException, IOException, UserException, ProductException;
    ProductDTO updateProduct(Long id, ProductDTO productDTO, List<MultipartFile> images) throws CategoryException, BrandException, IOException, UserException, ProductException;
    void deleteProduct(Long id) throws ProductException, UserException;
    List<ProductDTO> getAllProducts() throws Exception;
    ProductDTO getProductById(Long id) throws Exception;
    List<ProductDTO> getProductsByCategory(Long categoryId) throws CategoryException;
    List<ProductDTO> getProductsByBrand(Long brandId) throws BrandException;
    List<ProductDTO> getProductsByCategoryAndBrand(Long categoryId, Long brandId) throws CategoryException, BrandException;
    List<ProductDTO> searchByKeyword(String keyword);
}
