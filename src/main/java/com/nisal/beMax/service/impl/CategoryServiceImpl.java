package com.nisal.beMax.service.impl;

import com.nisal.beMax.domain.UserRole;
import com.nisal.beMax.exceptions.CategoryException;
import com.nisal.beMax.exceptions.UserException;
import com.nisal.beMax.mapper.BrandMapper;
import com.nisal.beMax.mapper.CategoryMapper;
import com.nisal.beMax.model.Brand;
import com.nisal.beMax.model.Category;
import com.nisal.beMax.model.User;
import com.nisal.beMax.payload.dto.CategoryDTO;
import com.nisal.beMax.repository.CategoryRepository;
import com.nisal.beMax.service.CategoryService;
import com.nisal.beMax.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Override
    public CategoryDTO createCategory(CategoryDTO dto) throws CategoryException, UserException {
        User user = userService.getCurrentUser();


        Category category = Category.builder()
                .name(dto.getName())
                .build();

        checkAuthority(user);
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) throws CategoryException, UserException {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new CategoryException("category not exist")
        );
        User user = userService.getCurrentUser();

        category.setName(dto.getName());

        checkAuthority(user);
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDTO> getAllCategories() throws CategoryException {
        List<Category> dtos = categoryRepository.findAll();
        if (dtos.isEmpty()) {
            throw new CategoryException("category not exist");
        }
        return dtos.stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) throws CategoryException, UserException {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new CategoryException("category not exist")
        );
        User user = userService.getCurrentUser();

        checkAuthority(user);
        categoryRepository.delete(category);

    }

    private void checkAuthority(User user) throws AccessDeniedException {
        boolean isUser = user.getRole().equals(UserRole.ROLE_USER);

        if(isUser){
            throw new AccessDeniedException("you don't have permission to manage this category");
        }
    }
}
