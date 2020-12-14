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
import java.util.ArrayList;
import java.util.List;

@Validated
@Entity
@Table(name = "webshop_order")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 6, fraction = 2)
    @Column(name = "total_price_hrk")
    private BigDecimal totalPriceHrk;

    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 6, fraction = 2)
    @Column(name = "total_price_eur")
    private BigDecimal totalPriceEur;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public enum Status {
        DRAFT, SUBMITTED;
    }

}
