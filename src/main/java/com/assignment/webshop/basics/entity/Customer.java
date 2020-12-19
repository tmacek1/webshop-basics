package com.assignment.webshop.basics.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Data
@SequenceGenerator(name="customer_seq", initialValue=10, allocationSize=100)
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="customer_seq")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

}
