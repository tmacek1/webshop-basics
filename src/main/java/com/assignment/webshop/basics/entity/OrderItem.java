package com.assignment.webshop.basics.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "webshop_order_item")
@Data
@SequenceGenerator(name="webshop_order_item_seq", initialValue=10, allocationSize=100)
public class OrderItem {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="webshop_order_item_seq")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

}
