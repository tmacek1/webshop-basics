package com.assignment.webshop.basics;

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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderItemControllerWebLayerTest {

    @Autowired
    MockMvc mockMvc;

    String jsonRequest = "{\"quantity\":1,\"order_id\":1,\"product_id\":1}";
    String jsonResponse = "{\"id\":10,\"quantity\":1,\"order_id\":1,\"product_id\":1}";

    @Test
    @Order(1)
    public void testCreateNewOrderItem() throws Exception {
        this.mockMvc.perform(
                post("/webshop/api/v1/orderitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponse))
                .andDo(document("orderitempost", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    @Order(2)
    public void testDeleteOrderItem() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/webshop/api/v1/orderitems/10"))
                .andExpect(status().isAccepted())
                .andDo(document("orderitemdelete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

    }

}
