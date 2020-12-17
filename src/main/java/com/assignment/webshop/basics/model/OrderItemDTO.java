package com.assignment.webshop.basics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
@Data
public class OrderItemDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @Min(0)
    private Integer quantity;

    @NotNull
    @JsonProperty("order_id")
    private Long orderId;

    @NotNull
    @JsonProperty("product_id")
    private Long productId;

}
