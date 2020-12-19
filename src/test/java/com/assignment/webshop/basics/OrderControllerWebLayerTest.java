package com.assignment.webshop.basics;

import com.assignment.webshop.basics.entity.Customer;
import com.assignment.webshop.basics.entity.Order;
import com.assignment.webshop.basics.entity.OrderItem;
import com.assignment.webshop.basics.model.CustomerDTO;
import com.assignment.webshop.basics.model.OrderDTO;
import com.assignment.webshop.basics.model.OrderItemJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerWebLayerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public static class TestObjects {
        public static Order aOrder() {

            Customer customer = new Customer();
            customer.setId(1L);
            customer.setEmail("tomislav.macek4@gmail.com");
            customer.setFirstName("Tomislav");
            customer.setLastName("Macek");

            List<OrderItem> orderItemList = new ArrayList<>();

            Order order = new Order();
            order.setId(1L);
            order.setCustomer(customer);
            order.setOrderItems(orderItemList);
            order.setStatus(Order.Status.DRAFT);
            order.setTotalPriceEur(new BigDecimal("0.00"));
            order.setTotalPriceHrk(new BigDecimal("0.00"));
            return order;
        }

        public static OrderDTO jsonObject() {

            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(1L);
            customerDTO.setFirstName("Tomislav");
            customerDTO.setLastName("Macek");
            customerDTO.setEmail("tomislav.macek4@gmail.com");

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(1L);
            //orderDTO.setCustomer(customerDTO);
            orderDTO.setOrderItemJson(null);
            orderDTO.setStatus("DRAFT");
            orderDTO.setTotalPriceEur(new BigDecimal("0.00"));
            orderDTO.setTotalPriceHrk(new BigDecimal("0.00"));
            return orderDTO;
        }
    }


    @Test
    @org.junit.jupiter.api.Order(2)
    public void testGetOrderById() throws Exception {
        this.mockMvc.perform(
                get("/webshop/api/v1/orders/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string((objectMapper.writeValueAsString(TestObjects.jsonObject()))))
                .andDo(document("ordergetbyid", preprocessResponse(prettyPrint())));
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testCreateNewOrder() throws Exception {
        this.mockMvc.perform(
                post("/webshop/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TestObjects.jsonObject())))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(TestObjects.jsonObject())))
                .andDo(document("orderpost", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }


}






