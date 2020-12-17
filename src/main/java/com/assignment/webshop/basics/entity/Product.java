package com.assignment.webshop.basics.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code", unique = true, length = 10, nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @DecimalMin(value = "0.0")
    @Digits(integer = 6, fraction = 2)
    @Column(name = "price_hrk", precision = 6, scale = 2, nullable = false)
    private BigDecimal priceHrk;

    @Column(name = "description")
    private String description;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

}
