package com.assignment.webshop.basics.repository;

import com.assignment.webshop.basics.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCode(String code);
    Optional<Product> findById(long id);
}
