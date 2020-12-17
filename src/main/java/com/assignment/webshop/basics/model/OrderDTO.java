package com.assignment.webshop.basics.model;

import com.assignment.webshop.basics.entity.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;

@Validated
@Data
@Setter
public class OrderDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String status;

    @DecimalMin(value = "0.0")
    @Digits(integer = 6, fraction = 2)
    @JsonProperty("total_price_hrk")
    private BigDecimal totalPriceHrk;

    @DecimalMin(value = "0.0")
    @Digits(integer = 6, fraction = 2)
    @JsonProperty("total_price_eur")
    private BigDecimal totalPriceEur;

    private Customer customer;

    @JsonProperty("customer_id")
    private void setCustomerField(Long customer_id) {
        this.customer = new Customer();
        customer.setId(customer_id);
    }

    @JsonProperty("order_item")
    private List<OrderItemJson> orderItemJson;

}
