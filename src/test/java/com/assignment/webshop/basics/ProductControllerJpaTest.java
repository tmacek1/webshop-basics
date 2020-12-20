package com.assignment.webshop.basics;

import com.assignment.webshop.basics.model.Product;
import com.assignment.webshop.basics.repository.ProductRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(false)
public class ProductControllerJpaTest {

    @Autowired
    private ProductRepository productRepository;

    public static class TestObjects {
        public static Product aProduct() {
            Product product = new Product();
            product.setCode("1234");
            product.setName("testProduct");
            product.setAvailable(true);
            product.setDescription("testDesc");
            product.setPriceHrk(new BigDecimal("55"));
            return product;
        }
    }

    @Test
    @Order(1)
    public void testSaveProduct() {

        Product targetProduct = productRepository.saveAndFlush(TestObjects.aProduct());
        assertThat(targetProduct.getName()).isEqualTo("testProduct");
    }

    @Test
    @Order(2)
    public void testGetProductByCode() {

        Optional<Product> product = productRepository.findByCode(TestObjects.aProduct().getCode());
        assertThat(product.isPresent()).isTrue();
        assertThat(product.get().getName()).isEqualTo("testProduct");
    }


    @Test
    @Order(3)
    public void testUpdateProduct() {

        Optional<Product> product = productRepository.findByCode(TestObjects.aProduct().getCode());
        assertThat(product.isPresent()).isTrue();
        Product targetProduct = product.get();
        targetProduct.setName("updateProduct");
        productRepository.saveAndFlush(targetProduct);
        Optional<Product> updatedProduct = productRepository.findByCode(TestObjects.aProduct().getCode());
        assertThat(updatedProduct.get().getName()).isEqualTo("updateProduct");
    }

    @Test
    @Order(4)
    public void testDeleteProduct() {

        Optional<Product> product = productRepository.findByCode(TestObjects.aProduct().getCode());
        assertThat(product.isPresent()).isTrue();
        productRepository.deleteById(product.get().getId());
        Optional<Product> targetProduct = productRepository.findByCode(TestObjects.aProduct().getCode());
        assertThat(targetProduct.isPresent()).isFalse();
    }
}