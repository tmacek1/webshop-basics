package com.assignment.webshop.basics.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Validated
@Entity
@SequenceGenerator(name = "product_seq", initialValue = 1, allocationSize = 100)
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    private long id;

    @Column(name = "code", unique = true, length = 10, nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 6, fraction = 2)
    @Column(name = "price_hrk", nullable = false)
    private BigDecimal priceHrk;

    @Column(name = "description")
    private String description;

    @Column(name = "is_available", nullable = false)
    @JsonProperty("is_available")
    private boolean isAvailable;

//    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @PrimaryKeyJoinColumn
//    private OrderItem orderItem;


}
