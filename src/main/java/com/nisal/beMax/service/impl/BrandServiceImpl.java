package com.nisal.beMax.service.impl;

import com.nisal.beMax.domain.UserRole;
import com.nisal.beMax.exceptions.BrandException;
import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.mapper.BrandMapper;
import com.nisal.beMax.model.Brand;
import com.nisal.beMax.model.User;
import com.nisal.beMax.payload.dto.BrandDTO;
import com.nisal.beMax.repository.BrandRepository;
import com.nisal.beMax.repository.CategoryRepository;
import com.nisal.beMax.service.BrandService;
import com.nisal.beMax.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final BrandRepository brandRepository;

    @Override
    public BrandDTO createBrand(BrandDTO dto) throws BrandException, UserException {
        User user = userService.getCurrentUser();

        Brand brand = brandRepository.findByName(dto.getName());
        if (brand != null) {
            throw new BrandException("brand already registered !");
        }

        Brand newBrand = Brand.builder()
                .name(dto.getName())
                .build();

        checkAuthority(user);

        return BrandMapper.toDTO(brandRepository.save(newBrand));
    }

    @Override
    public BrandDTO updateBrand(Long id, BrandDTO dto) throws BrandException, UserException {
        Brand brand = brandRepository.findById(id).orElseThrow(
                () -> new BrandException("brand does not exist !")
        );

        User user = userService.getCurrentUser();

        if (brandRepository.findByName(dto.getName()) != null) {
            throw new BrandException("brand name already registered !");
        }

        brand.setName(dto.getName());
        checkAuthority(user);

        return BrandMapper.toDTO(brandRepository.save(brand));
    }

    @Override
    public void deleteBrand(Long id) throws BrandException, UserException {
        Brand brand = brandRepository.findById(id).orElseThrow(
                () -> new BrandException("brand does not exist !")
        );
        User user = userService.getCurrentUser();
        checkAuthority(user);
        brandRepository.delete(brand);

    }

    @Override
    public List<BrandDTO> getAllBrands() throws BrandException {
        List<Brand> dtos = brandRepository.findAll();

        return dtos.stream().map(BrandMapper::toDTO).collect(Collectors.toList());

    }

    private void checkAuthority(User user) throws AccessDeniedException {
        boolean isUser = user.getRole().equals(UserRole.ROLE_USER);

        if(isUser){
            throw new AccessDeniedException("you don't have permission to manage this category");
        }
    }
}
