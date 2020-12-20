package com.assignment.webshop.basics.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "webshop_order")
@Data
@SequenceGenerator(name = "webshop_order_seq", initialValue = 10, allocationSize = 100)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "webshop_order_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Digits(integer = 6, fraction = 2)
    @Column(name = "total_price_hrk")
    private BigDecimal totalPriceHrk;

    @Digits(integer = 6, fraction = 2)
    @Column(name = "total_price_eur")
    private BigDecimal totalPriceEur;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public enum Status {
        DRAFT, SUBMITTED;
    }
}
