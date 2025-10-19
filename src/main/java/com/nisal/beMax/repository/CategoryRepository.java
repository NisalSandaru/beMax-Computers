package com.nisal.beMax.repository;

import com.nisal.beMax.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
