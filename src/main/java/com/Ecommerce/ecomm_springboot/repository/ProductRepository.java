package com.Ecommerce.ecomm_springboot.repository;

import com.Ecommerce.ecomm_springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
