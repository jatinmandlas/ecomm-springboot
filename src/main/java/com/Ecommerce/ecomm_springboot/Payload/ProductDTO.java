package com.Ecommerce.ecomm_springboot.Payload;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String productName;

    private String Description;
    private String image;
    private Integer quantity;
    private Double price;
    private Double discount;

    private Double specialPrice;


}
