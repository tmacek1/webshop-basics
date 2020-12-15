package com.assignment.webshop.basics.controller;

import com.assignment.webshop.basics.model.Product;
import com.assignment.webshop.basics.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/webshop/api/v1")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(value = "/products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product saveProduct = productService.createProduct(product);
        return new ResponseEntity<Product>(saveProduct, HttpStatus.CREATED);
    }

    @PutMapping(value = "/products")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {

        Product updateProduct = productService.updateProduct(product);
        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/products/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> readProduct(@PathVariable String code) {

        Optional<Product> product = productService.getProduct(code);

        if (product.isPresent()) {
            return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "product not found"
            );
        }
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
