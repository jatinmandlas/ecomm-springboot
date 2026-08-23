package com.Ecommerce.ecomm_springboot.service;

import com.Ecommerce.ecomm_springboot.Payload.CartDTO;
import com.Ecommerce.ecomm_springboot.Payload.ProductDTO;
import com.Ecommerce.ecomm_springboot.Payload.ProductResponse;
import com.Ecommerce.ecomm_springboot.exceptions.APIException;
import com.Ecommerce.ecomm_springboot.exceptions.ResourceNotFoundException;
import com.Ecommerce.ecomm_springboot.model.Cart;
import com.Ecommerce.ecomm_springboot.model.Category;
import com.Ecommerce.ecomm_springboot.model.Product;
import com.Ecommerce.ecomm_springboot.repository.CartRepository;
import com.Ecommerce.ecomm_springboot.repository.CategoryRepository;
import com.Ecommerce.ecomm_springboot.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
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
        List<Product> products=category.getProducts();
        boolean productnotpresent=true;
        for(Product p:products){
            if(p.getProductName().equals(productDTO.getProductName())){
                productnotpresent=false;
                break;
            }
        }
        if(productnotpresent){
            Product product=modelMapper.map(productDTO,Product.class);
            product.setCategory(category);
            double specialPrice=product.getPrice()-((product.getDiscount()*0.01)*product.getPrice());
            product.setSpecialPrice(specialPrice);
            product.setImage("image.url");
            Product savedProduct=productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);

        }else{
            throw new APIException("Product not found");
        }








    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> pageProducts = productRepository.findAll(pageDetails);


        List<Product> products=pageProducts.getContent();
        if (products.isEmpty())
            throw new APIException("No product created till now.");
       List<ProductDTO> coproducts = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(coproducts);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;

    }

    @Override
    public ProductResponse getAllProductsByCategory(Long categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> pageProducts = productRepository.findByCategory(category,pageDetails);


        List<Product> products=pageProducts.getContent();

        if (products.isEmpty())
            throw new APIException("No product  of this category is created till now.");


        List<ProductDTO> coproducts = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(coproducts);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;



    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> pageProducts = productRepository.findByProductNameLikeIgnoreCase('%' +keyword +'%',pageDetails);


        List<Product> products=pageProducts.getContent();




        List<ProductDTO> coproducts = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(coproducts);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

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
        Product savedProduct =productRepository.save(product);
        List<Cart>  carts=cartRepository.findCartsByProductId(productId);
        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> products = cart.getItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

            cartDTO.setProducts(products);

            return cartDTO;

        }).toList();

        cartDTOs.forEach(cart -> cartService.updateProductInCarts(cart.getCartId(), productId));
        return modelMapper.map(savedProduct, ProductDTO.class);

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
