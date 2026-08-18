package com.Ecommerce.ecomm_springboot.repository;

import com.Ecommerce.ecomm_springboot.model.Category;
import com.Ecommerce.ecomm_springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategory(Category category);
    List<Product> findByProductNameLikeIgnoreCase(String keyword);
    Product findByProductName(String productName);



}
