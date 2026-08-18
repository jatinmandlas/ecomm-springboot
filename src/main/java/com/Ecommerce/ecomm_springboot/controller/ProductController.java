package com.Ecommerce.ecomm_springboot.controller;


import com.Ecommerce.ecomm_springboot.Payload.ProductDTO;
import com.Ecommerce.ecomm_springboot.Payload.ProductResponse;
import com.Ecommerce.ecomm_springboot.model.Category;
import com.Ecommerce.ecomm_springboot.model.Product;
import com.Ecommerce.ecomm_springboot.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoryId) {
        ProductDTO savedproductDTO = productService.addProduct(productDTO,categoryId);
        return new ResponseEntity<>(savedproductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getAllProductsByCategory(@PathVariable Long categoryId) {
        ProductResponse productResponse=productService.getAllProductsByCategory(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getAllProductsByKeyword(@PathVariable String keyword) {
        ProductResponse productResponse=productService.getProductsByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId){
        ProductDTO updatedProductDTO = productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }
    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@RequestParam("image") MultipartFile image, @PathVariable Long productId) throws IOException {
        ProductDTO product=productService.updateProductImage(productId,image);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
