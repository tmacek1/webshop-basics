package com.assignment.webshop.basics.controller;

import com.assignment.webshop.basics.entity.Product;
import com.assignment.webshop.basics.model.ProductDTO;
import com.assignment.webshop.basics.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/webshop/api/v1")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ProductDTO> readProduct(
            @PathVariable(required = false) long id) {

        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            ProductDTO productDTO = modelMapper.map(product.get(), ProductDTO.class);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "product id not found"
            );
        }
    }

    @GetMapping(value = "/products/")
    public ResponseEntity<ProductDTO> readProductByCode(
            @RequestParam(value = "code", required = false) String code) {

        Optional<Product> product = productService.getProductByCode(code);
        if (product.isPresent()) {
            ProductDTO productDTO = modelMapper.map(product.get(), ProductDTO.class);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "product code not found"
            );
        }
    }

    @PostMapping(value = "/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);
        Product saveProduct = productService.createProduct(product);
        ProductDTO productResult = modelMapper.map(saveProduct, ProductDTO.class);
        return new ResponseEntity<>(productResult, HttpStatus.CREATED);
    }

    @PutMapping(value = "/products")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);
        Product updateProduct = productService.updateProduct(product);
        ProductDTO result = modelMapper.map(updateProduct, ProductDTO.class);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
