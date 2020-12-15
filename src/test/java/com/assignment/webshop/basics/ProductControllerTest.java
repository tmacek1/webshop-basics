package com.assignment.webshop.basics;

import com.assignment.webshop.basics.model.Product;
import com.assignment.webshop.basics.repository.ProductRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(false)
public class ProductControllerTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Order(1)
    public void testSaveProduct() {

        Product product = new Product(
                1,
                "1234",
                "testProduct",
                new BigDecimal("99.99"),
                "testDescription",
                true);

        Product targetProduct = productRepository.saveAndFlush(product);
        assertThat(targetProduct.getName()).isEqualTo("testProduct");
    }

    @Test
    @Order(2)
    public void testGetProductByCode() {
        Optional<Product> product = productRepository.findByCode("1234");
        assertThat(product.get().getName()).isEqualTo("testProduct");
    }


    @Test
    @Order(3)
    public void testUpdateProduct() {

        Optional<Product> product = productRepository.findByCode("1234");
        Product targetProduct = product.get();
        targetProduct.setName("updateProduct");

        productRepository.saveAndFlush(targetProduct);

        Optional<Product> updatedProduct = productRepository.findByCode("1234");
        assertThat(updatedProduct.get().getName()).isEqualTo("updateProduct");
    }

    @Test
    @Order(4)
    public void testDeleteProduct() {

        Optional<Product> product = productRepository.findByCode("1234");
        productRepository.deleteById(product.get().getId());
        Optional<Product> targetProduct = productRepository.findByCode("1234");
        assertThat(targetProduct.isPresent()).isEqualTo(false);
    }
}