package com.assignment.webshop.basics.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webshop/api/v1")
@Slf4j
public class OrderController {

    @PostMapping(value = "/orders")

    public void createOder() {
    }

    @PutMapping(value = "/orders/{id}")
    public void updateOrder() {
    }

    @GetMapping(value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void readOrder() {
    }

    @DeleteMapping(value = "/orders/{id}")
    public void deleteOrder() {
    }

}