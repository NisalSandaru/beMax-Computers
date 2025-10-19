package com.nisal.beMax.service.impl;

import com.nisal.beMax.domain.ProductCondition;
import com.nisal.beMax.domain.UserRole;
import com.nisal.beMax.exceptions.BrandException;
import com.nisal.beMax.exceptions.CategoryException;
import com.nisal.beMax.exceptions.ProductException;
import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.mapper.ProductMapper;
import com.nisal.beMax.model.*;
import com.nisal.beMax.payload.dto.ProductDTO;
import com.nisal.beMax.repository.BrandRepository;
import com.nisal.beMax.repository.CategoryRepository;
import com.nisal.beMax.repository.ProductRepository;
import com.nisal.beMax.repository.UserRepository;
import com.nisal.beMax.service.BrandService;
import com.nisal.beMax.service.FileStorageService;
import com.nisal.beMax.service.ProductService;
import com.nisal.beMax.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final FileStorageService fileStorageService;
    private final UserService userService;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, List<MultipartFile> images)
            throws CategoryException, BrandException, IOException, UserException, ProductException {

        // ✅ 1. Validate SKU
        if (productDTO.getSku() == null || productDTO.getSku().isBlank()) {
            throw new ProductException("SKU must be provided");
        }

        if (productRepository.existsBySku(productDTO.getSku())) {
            throw new ProductException("SKU already exists: " + productDTO.getSku());
        }

        // ✅ 2. Find category & brand
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new CategoryException("Category not found"));

        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new BrandException("Brand not found"));

        // ✅ 3. Map DTO → Entity
        Product product = ProductMapper.toEntity(productDTO, category, brand);

        // ✅ 4. Handle images
        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                String imagePath = fileStorageService.saveProductImage(file);
                ProductImage image = new ProductImage();
                image.setImagePath(imagePath);
                image.setProduct(product);
                product.getImages().add(image);
            }
        }

        // ✅ 5. Check authority
        User user = userService.getCurrentUser();
        checkAuthority(user);

        // ✅ 6. Save product
        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, List<MultipartFile> images)
            throws CategoryException, BrandException, IOException, UserException, ProductException {

        // 1️⃣ Find existing product
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with id: " + id));

        // 2️⃣ Check authority
        User user = userService.getCurrentUser();
        checkAuthority(user);

        // 3️⃣ Validate SKU if changed
        if (productDTO.getSku() != null && !productDTO.getSku().equals(existing.getSku())) {
            if (productRepository.existsBySku(productDTO.getSku())) {
                throw new ProductException("SKU already exists: " + productDTO.getSku());
            }
            existing.setSku(productDTO.getSku());
        }

        // 4️⃣ Update other fields
        if (productDTO.getName() != null) existing.setName(productDTO.getName());
        if (productDTO.getProductCondition() != null) existing.setProductCondition(productDTO.getProductCondition());
        if (productDTO.getStockQty() != null) existing.setStockQty(productDTO.getStockQty());
        if (productDTO.getDescription() != null) existing.setDescription(productDTO.getDescription());
        if (productDTO.getMrp() != null) existing.setMrp(productDTO.getMrp());
        if (productDTO.getSellingPrice() != null) existing.setSellingPrice(productDTO.getSellingPrice());

        // 5️⃣ Update category if changed
        if (productDTO.getCategoryId() != null && !productDTO.getCategoryId().equals(existing.getCategory().getId())) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new CategoryException("Category not found"));
            existing.setCategory(category);
        }

        // 6️⃣ Update brand if changed
        if (productDTO.getBrandId() != null && !productDTO.getBrandId().equals(existing.getBrand().getId())) {
            Brand brand = brandRepository.findById(productDTO.getBrandId())
                    .orElseThrow(() -> new BrandException("Brand not found"));
            existing.setBrand(brand);
        }

        // 7️⃣ Handle new images
        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                String imagePath = fileStorageService.saveProductImage(file);
                ProductImage image = new ProductImage();
                image.setImagePath(imagePath);
                image.setProduct(existing);
                existing.getImages().add(image);
            }
        }

        // 8️⃣ Save updated product
        Product updated = productRepository.save(existing);

        return ProductMapper.toDTO(updated);
    }

    @Override
    public void deleteProduct(Long id) throws ProductException, UserException {
        // 1️⃣ Find product
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with id: " + id));

        // 2️⃣ Check authority
        User user = userService.getCurrentUser();
        checkAuthority(user);

        // 3️⃣ Optional: delete images from local storage
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            for (ProductImage image : product.getImages()) {
                try {
                    Path path = Paths.get("uploads/products/" + Paths.get(image.getImagePath()).getFileName());
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    System.err.println("Failed to delete image: " + image.getImagePath());
                }
            }
        }

        // 4️⃣ Delete product (images removed automatically due to cascade & orphanRemoval)
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> dtos = productRepository.findAll();

        return dtos.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) throws ProductException {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new ProductException("Product not found")
        );
        return ProductMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId) throws CategoryException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException("Category not found"));

        return productRepository.findByCategory(category)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByBrand(Long brandId) throws BrandException {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandException("Brand not found"));

        return productRepository.findByBrand(brand)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByCategoryAndBrand(Long categoryId, Long brandId) throws CategoryException, BrandException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException("Category not found"));

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandException("Brand not found"));

        return productRepository.findByCategoryAndBrand(category, brand)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDTO> searchByKeyword(String keyword) {
        return productRepository.searchByKeyword(keyword)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    private void checkAuthority(User user) throws AccessDeniedException {
        boolean isUser = user.getRole().equals(UserRole.ROLE_USER);

        if(isUser){
            throw new AccessDeniedException("you don't have permission to manage this product");
        }
    }
}
