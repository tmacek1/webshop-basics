package com.assignment.webshop.basics.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Validated
@Entity
@Table(name = "webshop_order_item")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Min(0)
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    private Order order;

    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
