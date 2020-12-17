package com.assignment.webshop.basics.controller;

import com.assignment.webshop.basics.entity.OrderItem;
import com.assignment.webshop.basics.model.OrderItemDTO;
import com.assignment.webshop.basics.exception.OrderItemException;
import com.assignment.webshop.basics.service.OrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/webshop/api/v1")
public class OrderItemController {

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(value = "/orderitems")
    public OrderItemDTO postOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) {

        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);

        try {
            OrderItemDTO result = modelMapper.map(orderItemService.createOrderItem(orderItem), OrderItemDTO.class);
            return result;
        } catch (OrderItemException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage());

        }
    }
}


