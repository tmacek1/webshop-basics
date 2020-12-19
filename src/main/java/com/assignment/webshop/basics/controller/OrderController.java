package com.assignment.webshop.basics.controller;

import com.assignment.webshop.basics.entity.Order;
import com.assignment.webshop.basics.entity.OrderItem;
import com.assignment.webshop.basics.exception.OrderException;
import com.assignment.webshop.basics.model.CustomerDTO;
import com.assignment.webshop.basics.model.OrderDTO;
import com.assignment.webshop.basics.model.OrderItemJson;
import com.assignment.webshop.basics.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/webshop/api/v1")
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ModelMapper modelMapper;


    @GetMapping(value = "/orders/{id}")
    public ResponseEntity<OrderDTO> readOrder(
            @PathVariable(required = false) long id) {

        log.info("get order by id:{}", id);
        Optional<Order> order = orderService.getOrderById(id);

        if (order.isPresent()) {

            OrderDTO orderDTO = modelMapper.map(order.get(), OrderDTO.class);
            CustomerDTO customerDTO = modelMapper.map(order.get().getCustomer(), CustomerDTO.class);

            List<OrderItemJson> orderItemJsonList = new ArrayList<>();

            for (OrderItem orderItem : order.get().getOrderItems()) {
                OrderItemJson orderItemJson = new OrderItemJson();
                orderItemJson.setId(orderItem.getId());
                orderItemJson.setQuantity(orderItem.getQuantity());
                orderItemJson.setProductField(orderItem.getProduct());
                orderItemJsonList.add(orderItemJson);
            }

            orderDTO.setCustomerDTO(customerDTO);
            orderDTO.setOrderItemJson(orderItemJsonList);
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "order id not found"
            );
        }
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody Order order) {

        log.info("post new order");
        Optional<Order> saveOrder = orderService.createOrder(order);

        if (saveOrder.isPresent()) {

            OrderDTO returnOrderDTO = modelMapper.map(saveOrder.get(), OrderDTO.class);
            CustomerDTO customerDTO = modelMapper.map(saveOrder.get().getCustomer(), CustomerDTO.class);
            returnOrderDTO.setCustomerDTO(customerDTO);
            return new ResponseEntity<>(returnOrderDTO, HttpStatus.CREATED);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "customer not present"
            );
        }
    }

    @PutMapping(value = "/orders")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order) {

        log.info("put new order");
        Optional<Order> updateOrder = orderService.updateOrder(order);
        if (updateOrder.isPresent()) {
            return new ResponseEntity<>(updateOrder.get(), HttpStatus.ACCEPTED);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "mandatory param not found"
        );
    }

    @DeleteMapping(value = "/orders/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable long id) {

        log.info("delete order by id: {}", id);
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/orders/final/{id}")
    public ResponseEntity<OrderDTO> finalizeOrder(@PathVariable(required = true) long id) {

        log.info("finalize order");

        try {
            orderService.finalizeOrder(id);
            return readOrder(id);
        } catch (OrderException e) {
            throw new ResponseStatusException(
                    HttpStatus.ACCEPTED, e.getMessage());
        }
    }

}