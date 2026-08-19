package com.Ecommerce.ecomm_springboot.service;

import com.Ecommerce.ecomm_springboot.Payload.ProductDTO;
import com.Ecommerce.ecomm_springboot.Payload.ProductResponse;
import com.Ecommerce.ecomm_springboot.model.Category;
import com.Ecommerce.ecomm_springboot.model.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);



    ProductResponse getAllProductsByCategory(Long categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductsByKeyword(String keyword,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(Long productId, @Valid ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}
