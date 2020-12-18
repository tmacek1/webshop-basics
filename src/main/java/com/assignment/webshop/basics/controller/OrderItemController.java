package com.assignment.webshop.basics.controller;

import com.assignment.webshop.basics.entity.OrderItem;
import com.assignment.webshop.basics.model.OrderItemDTO;
import com.assignment.webshop.basics.exception.OrderItemException;
import com.assignment.webshop.basics.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/webshop/api/v1")
@Slf4j
public class OrderItemController {

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(value = "/orderitems")
    public OrderItemDTO postOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) {

        log.info("post order item");
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);

        try {
            return modelMapper.map(orderItemService.createOrderItem(orderItem), OrderItemDTO.class);
        } catch (OrderItemException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage());

        }
    }

    @DeleteMapping(value = "/orderitems/{id}")
    public ResponseEntity<HttpStatus> deleteOrderItem(@PathVariable long id) {

        log.info("delete order item by id: {}", id);
        orderItemService.deleteOrderItem(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}


