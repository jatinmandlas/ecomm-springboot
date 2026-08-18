package com.Ecommerce.ecomm_springboot.service;

import com.Ecommerce.ecomm_springboot.Payload.ProductDTO;
import com.Ecommerce.ecomm_springboot.Payload.ProductResponse;
import com.Ecommerce.ecomm_springboot.exceptions.APIException;
import com.Ecommerce.ecomm_springboot.exceptions.ResourceNotFoundException;
import com.Ecommerce.ecomm_springboot.model.Category;
import com.Ecommerce.ecomm_springboot.model.Product;
import com.Ecommerce.ecomm_springboot.repository.CategoryRepository;
import com.Ecommerce.ecomm_springboot.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
        Product product=modelMapper.map(productDTO,Product.class);
        Product productfromDB=productRepository.findByProductName(product.getProductName());
        if (productfromDB != null)
            throw new APIException("Category with the name " + product.getProductName() + " already exists !!!");



        product.setCategory(category);
        double specialPrice=product.getPrice()-((product.getDiscount()*0.01)*product.getPrice());
        product.setSpecialPrice(specialPrice);
        product.setImage("image.url");
        Product savedProduct=productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);



    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products=productRepository.findAll();
        if (products.isEmpty())
            throw new APIException("No product created till now.");
       List<ProductDTO> coproducts = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(coproducts);
        return productResponse;

    }

    @Override
    public ProductResponse getAllProductsByCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
        List<Product> products=productRepository.findByCategory(category);

        List<ProductDTO> coproducts = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(coproducts);
        return productResponse;



    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        List<Product> products=productRepository.findByProductNameLikeIgnoreCase('%' +keyword +'%');

        List<ProductDTO> coproducts = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(coproducts);
        return productResponse;

    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product product=productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("product","productId",productId));
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setSpecialPrice(productDTO.getSpecialPrice());
        product.setImage("image.url");
        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);

    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product=productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("product","productId",productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);


    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDB=productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("product","productId",productId));
//        String path="images/";
        String fileName= fileService.uploadImage(path,image);
        productFromDB.setImage(fileName);
        Product updatedProduct= productRepository.save(productFromDB);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }


}
