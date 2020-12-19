package com.assignment.webshop.basics;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 8089)
public class OrderFinalizationTest {

    @Autowired
    MockMvc mockMvc;

    /*
    this test will use application.properties in /test/resources/
     */


    @Test
    public void TestGetEndPoint() throws Exception {

        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
        mockResponse.withStatus(200)
                .withBody("[{\"Broj tečajnice\":\"246\",\"Datum primjene\":\"19.12.2020\",\"Država\":\"EMU\",\"Šifra valute\":\"978\",\"Valuta\":\"EUR\",\"Jedinica\":1,\"Kupovni za devize\":\"7,503616\",\"Srednji za devize\":\"7,526195\",\"Prodajni za devize\":\"7,548774\"}]")
                .withHeader("Content-Type", "application/json");

        WireMock.stubFor(
                WireMock.get("/tecajn/v1?valuta=EUR")
                        .willReturn(mockResponse)
        );

        ResultActions resultActions = this.mockMvc.perform(
                post("/webshop/api/v1/orders/final/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":2,\"status\":\"SUBMITTED\",\"total_price_hrk\":60.00,\"total_price_eur\":7.95,\"customer\":{\"id\":1,\"email\":\"tomislav.macek4@gmail.com\",\"first_name\":\"Tomislav\",\"last_name\":\"Macek\"},\"order_item\":[{\"id\":1,\"quantity\":1,\"product\":{\"id\":2,\"code\":\"12345\",\"name\":\"productA\",\"description\":\"productDescription\",\"price_hrk\":10.00,\"is_available\":true}},{\"id\":2,\"quantity\":1,\"product\":{\"id\":1,\"code\":\"123456\",\"name\":\"productB\",\"description\":\"productDescription\",\"price_hrk\":50.00,\"is_available\":true}}]}"));
    }
}
