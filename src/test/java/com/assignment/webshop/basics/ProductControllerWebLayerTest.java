package com.assignment.webshop.basics;

import com.assignment.webshop.basics.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerWebLayerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public static class TestObjects {
        public static ProductDTO aProduct() {

            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(10L);
            productDTO.setCode("testCode");
            productDTO.setName("productA");
            productDTO.setDescription("productDescription");
            productDTO.setAvailable(true);
            productDTO.setPriceHrk(new BigDecimal("10.00"));

            return productDTO;
        }

        public static ProductDTO bProduct() {

            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(10L);
            productDTO.setCode("testCode");
            productDTO.setName("changeName");
            productDTO.setDescription("productDescription");
            productDTO.setAvailable(true);
            productDTO.setPriceHrk(new BigDecimal("10.00"));

            return productDTO;
        }

        public static ProductDTO jsonObject() {

            ProductDTO productDTO = new ProductDTO();
            productDTO.setCode("testCode");
            productDTO.setName("productA");
            productDTO.setDescription("productDescription");
            productDTO.setAvailable(true);
            productDTO.setPriceHrk(new BigDecimal("10.00"));

            return productDTO;
        }

        public static ProductDTO jsonObjectPut() {

            ProductDTO productDTO = new ProductDTO();
            productDTO.setCode("testCode");
            productDTO.setName("changeName");
            productDTO.setDescription("productDescription");
            productDTO.setAvailable(true);
            productDTO.setPriceHrk(new BigDecimal("10.00"));

            return productDTO;
        }
    }

    @Test
    @Order(3)
    public void testGetProductById() throws Exception {
        this.mockMvc.perform(
                get("/webshop/api/v1/products/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(TestObjects.aProduct())))
                .andDo(document("productgetbyid", preprocessResponse(prettyPrint())));
    }

    @Test
    @Order(2)
    public void testGetProductByCode() throws Exception {
        this.mockMvc.perform(
                get("/webshop/api/v1/products").
                        param("code", "testCode"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(TestObjects.aProduct())))
                .andDo(document("productgetbycode", preprocessResponse(prettyPrint())));
    }


    @Test
    @Order(1)
    public void testCreateNewProduct() throws Exception {
        this.mockMvc.perform(
                post("/webshop/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestObjects.jsonObject())))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(TestObjects.aProduct())))
                .andDo(document("productpost", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    @Order(4)
    public void testPutProduct() throws Exception {
        this.mockMvc.perform(
                put("/webshop/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestObjects.jsonObjectPut())))
                .andExpect(status().isAccepted())
                .andExpect(content().string(objectMapper.writeValueAsString(TestObjects.bProduct())))
                .andDo(document("productput", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }


    @Test
    @Order(5)
    public void testDeleteProduct() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/webshop/api/v1/products/{code}", "123456"))
                .andExpect(status().isAccepted())
                .andDo(document("productdelete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

    }
}
