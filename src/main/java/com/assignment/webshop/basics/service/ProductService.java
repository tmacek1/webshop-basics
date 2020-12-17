package com.assignment.webshop.basics.service;

import com.assignment.webshop.basics.entity.Product;
import com.assignment.webshop.basics.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts;
    }

    public Optional<Product> getProductById(long id) {
        Optional<Product> fetchProduct = productRepository.findById(id);
        if (fetchProduct.isPresent()) {
            Product getProduct = fetchProduct.get();
            return Optional.of(getProduct);
        }

        return Optional.empty();
    }

    public Optional<Product> getProductByCode(String code) {
        Optional<Product> fetchProduct = productRepository.findByCode(code);
        if (fetchProduct.isPresent()) {
            Product getProduct = fetchProduct.get();
            return Optional.of(getProduct);
        }

        return Optional.empty();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {

        Optional<Product> fetchProduct = productRepository.findByCode(product.getCode());
        if (fetchProduct.isPresent()) {
            Product targetProduct = fetchProduct.get();
            targetProduct.setAvailable(product.isAvailable());
            targetProduct.setDescription(product.getDescription());
            targetProduct.setName(product.getName());
            targetProduct.setPriceHrk(product.getPriceHrk());
            return productRepository.save(targetProduct);

        } else {
            return productRepository.save(product);
        }

    }

    public void deleteProduct(String code) {
        Optional<Product> fetchProduct = productRepository.findByCode(code);
        if (fetchProduct.isPresent()) {
            productRepository.deleteById(fetchProduct.get().getId());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "product not found"
            );
        }
    }

}
