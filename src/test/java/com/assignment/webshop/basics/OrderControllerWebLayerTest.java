package com.assignment.webshop.basics;

import com.assignment.webshop.basics.dto.OrderItemJson;
import com.assignment.webshop.basics.dto.ProductDTO;
import com.assignment.webshop.basics.model.Customer;
import com.assignment.webshop.basics.model.Order;
import com.assignment.webshop.basics.model.OrderItem;
import com.assignment.webshop.basics.dto.CustomerDTO;
import com.assignment.webshop.basics.dto.OrderDTO;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(1L);
            productDTO.setAvailable(true);
            productDTO.setCode("123456");
            productDTO.setAvailable(true);
            productDTO.setName("productB");
            productDTO.setDescription("productDescription");
            productDTO.setPriceHrk(new BigDecimal("50.00"));

            List<OrderItemJson> orderItemJsonList = new ArrayList<>();
            OrderItemJson orderItemJson = new OrderItemJson();
            orderItemJson.setId(1L);
            orderItemJson.setProductDTO(productDTO);
            orderItemJson.setQuantity(1);
            orderItemJsonList.add(orderItemJson);

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(1L);
            orderDTO.setCustomerDTO(customerDTO);
            orderDTO.setOrderItemJson(orderItemJsonList);
            orderDTO.setStatus("DRAFT");
            orderDTO.setTotalPriceEur(new BigDecimal("0.00"));
            orderDTO.setTotalPriceHrk(new BigDecimal("0.00"));
            return orderDTO;
        }
    }


    String response = "{\"id\":10,\"status\":\"DRAFT\",\"total_price_hrk\":0.00,\"total_price_eur\":0.00,\"customer\":{\"id\":1,\"email\":\"tomislav.macek4@gmail.com\",\"first_name\":\"Tomislav\",\"last_name\":\"Macek\"},\"order_item\":[]}";


    @Test
    @org.junit.jupiter.api.Order(2)
    public void testGetOrderById() throws Exception {
        this.mockMvc.perform(
                get("/webshop/api/v1/orders/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(document("ordergetbyid", preprocessResponse(prettyPrint())));
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testCreateNewOrder() throws Exception {

        this.mockMvc.perform(
                post("/webshop/api/v1/orders?customerId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(response))
                .andDo(document("orderpost", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testDeleteOrder() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/webshop/api/v1/orders/10"))
                .andExpect(status().isAccepted())
                .andDo(document("orderdelete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

    }


}






