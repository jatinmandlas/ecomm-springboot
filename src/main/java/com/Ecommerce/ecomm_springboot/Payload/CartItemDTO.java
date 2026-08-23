package com.Ecommerce.ecomm_springboot.Payload;

public class CartItemDTO {
    private Long cartItemId;
    private CartDTO cartDTO;
    private ProductDTO productDTO;
    private Integer quantity;
    private double discount;
    private double productPrice;
}
