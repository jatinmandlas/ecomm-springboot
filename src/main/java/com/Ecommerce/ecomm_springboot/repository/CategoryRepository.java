package com.Ecommerce.ecomm_springboot.repository;

import com.Ecommerce.ecomm_springboot.DTO.CategoryDTO;
import com.Ecommerce.ecomm_springboot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryDTO, Long> {
    Category findByCategoryName(String categoryName);
}
