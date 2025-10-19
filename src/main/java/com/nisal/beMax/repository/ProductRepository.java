package com.nisal.beMax.repository;

import com.nisal.beMax.model.Brand;
import com.nisal.beMax.model.Category;
import com.nisal.beMax.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 1️⃣ Search by keyword in name or description
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    // 2️⃣ Filter by category
    List<Product> findByCategory(Category category);

    // 3️⃣ Filter by brand
    List<Product> findByBrand(Brand brand);

    // 4️⃣ Filter by category AND brand
    List<Product> findByCategoryAndBrand(Category category, Brand brand);

    // 5️⃣ Optional: keyword + category + brand
    @Query("SELECT p FROM Product p WHERE " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:category IS NULL OR p.category = :category) " +
            "AND (:brand IS NULL OR p.brand = :brand)")
    List<Product> searchWithFilters(@Param("keyword") String keyword,
                                    @Param("category") Category category,
                                    @Param("brand") Brand brand);

    boolean existsBySku(String sku);

}
