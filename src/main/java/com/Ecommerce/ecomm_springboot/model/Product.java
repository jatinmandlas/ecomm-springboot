package com.Ecommerce.ecomm_springboot.model;

import com.Ecommerce.ecomm_springboot.Payload.CategoryResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(min=3,message="Product Name must be atleast of 3 size")

    private String productName;
    @NotBlank
    @Size(min=6,message="discription should be atleast of 6 size")
    private String Description;
    private String image;
    private Integer quantity;
    private Double price;
    private Double discount;

    private Double specialPrice;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
