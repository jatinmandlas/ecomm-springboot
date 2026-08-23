package com.Ecommerce.ecomm_springboot.repository;

import com.Ecommerce.ecomm_springboot.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findById(Long addressId);
}
