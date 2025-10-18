package com.nisal.beMax.service;

import com.nisal.beMax.exceptions.BrandException;
import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.payload.dto.BrandDTO;

import java.util.List;

public interface BrandService {
    BrandDTO createBrand(BrandDTO dto) throws BrandException, UserException;
    BrandDTO updateBrand(Long id,BrandDTO dto) throws BrandException, UserException;
    void deleteBrand(Long id) throws BrandException, UserException;
    List<BrandDTO> getAllBrands() throws BrandException;

}
