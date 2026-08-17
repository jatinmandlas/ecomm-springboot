package com.Ecommerce.ecomm_springboot.model;

import com.Ecommerce.ecomm_springboot.Payload.CategoryResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String Description;
    private Double price;
    private Integer quantity;
    private Double specialPrice;
    @ManyToOne
    @JoinColumn(name="category_id")
    private CategoryResponse category;
}
