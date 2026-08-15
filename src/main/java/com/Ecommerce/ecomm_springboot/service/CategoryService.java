package com.Ecommerce.ecomm_springboot.service;

import com.Ecommerce.ecomm_springboot.DTO.CategoryResponse;
import com.Ecommerce.ecomm_springboot.DTO.CategoryDTO;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService{
    CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);









}

