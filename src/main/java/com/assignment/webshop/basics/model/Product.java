package com.assignment.webshop.basics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Validated
@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "code", unique = true, length = 10)
    private String code;

    @Column(name = "name")
    private String name;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 6, fraction = 2)
    @Column(name = "price_hrk")
    private BigDecimal priceHrk;

    @Column(name = "description")
    private String description;

    @Column(name = "is_available")
    private boolean isAvailable;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private OrderItem orderItem;

}
