package com.assignment.webshop.basics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.math.BigDecimal;


@Validated
@Data
public class ProductDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotEmpty(message = "code cannot be empty")
    private String code;

    @NotEmpty(message = "name cannot be empty")
    private String name;

    @DecimalMin(value = "0.0", message = "price must be greater than zero")
    @Digits(integer = 6, fraction = 2, message = "wrong currency format")
    @JsonProperty("price_hrk")
    private BigDecimal priceHrk;

    private String description;

    @JsonProperty("is_available")
    private boolean isAvailable;

}
