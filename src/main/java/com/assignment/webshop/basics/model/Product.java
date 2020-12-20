package com.assignment.webshop.basics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "product_seq", initialValue = 10, allocationSize = 100)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
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

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderItem orderItem;
}
