package com.Ecommerce.ecomm_springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
    @NotBlank
     private String street;
    @NotBlank

     private String buildingName;
    @NotBlank

     private String city;
    @NotBlank
     private String state;
    @NotBlank
     private String country;
    @NotBlank
     private String pincode;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Address( String buildingName, String city, String country, String pincode, String state, String street) {

        this.buildingName = buildingName;
        this.city = city;
        this.country = country;
        this.pincode = pincode;
        this.state = state;
        this.street = street;
    }
}
