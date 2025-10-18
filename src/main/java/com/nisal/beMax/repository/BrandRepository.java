package com.nisal.beMax.repository;

import com.nisal.beMax.model.Brand;
import com.nisal.beMax.payload.dto.BrandDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByName(String name) ;
}
