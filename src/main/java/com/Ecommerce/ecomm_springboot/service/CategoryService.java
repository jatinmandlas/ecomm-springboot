package com.Ecommerce.ecomm_springboot.service;

import com.Ecommerce.ecomm_springboot.Payload.CategoryResponse;
import com.Ecommerce.ecomm_springboot.Payload.CategoryDTO;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService{
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);









}

