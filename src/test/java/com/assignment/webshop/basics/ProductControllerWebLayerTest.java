package com.assignment.webshop.basics;

import com.assignment.webshop.basics.model.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ProductControllerWebLayerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public static class TestObjects {
        public static ProductDTO aProduct() {

            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(1L);
            productDTO.setCode("12345");
            productDTO.setName("productA");
            productDTO.setDescription("productDescription");
            productDTO.setAvailable(true);
            productDTO.setPriceHrk(new BigDecimal("10.00"));

            return productDTO;
        }
    }

    @Test
    public void testGetByCode() throws Exception {
        this.mockMvc.perform(
                get("/webshop/api/v1/products").
                        param("code", "12345"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(TestObjects.aProduct())))
                .andDo(document("productgetbycode", preprocessResponse(prettyPrint()))); // asciidocs in target/generated-snippets/index/
    }
}
