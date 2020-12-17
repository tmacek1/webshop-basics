package com.assignment.webshop.basics.controller;

import com.assignment.webshop.basics.client.HnbRestClient;
import com.assignment.webshop.basics.entity.Order;
import com.assignment.webshop.basics.entity.OrderItem;
import com.assignment.webshop.basics.exception.OrderException;
import com.assignment.webshop.basics.model.OrderDTO;
import com.assignment.webshop.basics.model.OrderItemJson;
import com.assignment.webshop.basics.model.ProductDTO;
import com.assignment.webshop.basics.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    @GetMapping(value = "/orders/all")
    public ResponseEntity<List<OrderDTO>> readAllOrders() {

        List<Order> readAll = orderService.getAllOrders();
        List<OrderDTO> result = new ArrayList<>();
        List<OrderItemJson> orderItemJsonList = new ArrayList<>();
        OrderItemJson orderItemJson = new OrderItemJson();

        for (Order order : readAll) {

            for (OrderItem orderItem : order.getOrderItems()) {
                orderItemJson.setProductDTO(modelMapper.map(orderItem.getProduct(), ProductDTO.class));
                orderItemJsonList.add(orderItemJson);
            }
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            orderDTO.setOrderItemJson(orderItemJsonList);
            result.add(orderDTO);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/orders/{id}")
    public ResponseEntity<OrderDTO> readOrder(
            @PathVariable(required = false) long id) {

        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {

            OrderDTO orderDTO = modelMapper.map(order.get(), OrderDTO.class);
            List<OrderItemJson> orderItemJsonList = new ArrayList<>();
            OrderItemJson orderItemJson = new OrderItemJson();

            for (OrderItem orderItem : order.get().getOrderItems()) {
                orderItemJson.setId(orderItem.getId());
                orderItemJson.setQuantity(orderItem.getQuantity());
                orderItemJson.setProductField(orderItem.getProduct());
                orderItemJsonList.add(orderItemJson);
            }

            orderDTO.setOrderItemJson(orderItemJsonList);
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "order id not found"
            );
        }
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {

        Order order = modelMapper.map(orderDTO, Order.class);

        Optional<Order> saveOrder = orderService.createOrder(order);
        if (saveOrder.isPresent()) {

            OrderDTO returnOrderDTO = modelMapper.map(saveOrder.get(), OrderDTO.class);
            return new ResponseEntity<>(returnOrderDTO, HttpStatus.CREATED);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "customer not present"
            );
        }
    }

    @PutMapping(value = "/orders")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order) {

        Optional<Order> updateOrder = orderService.updateOrder(order);
        if (updateOrder.isPresent()) {
            return new ResponseEntity<>(updateOrder.get(), HttpStatus.ACCEPTED);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "mandatory param not found"
        );
    }

    @DeleteMapping(value = "/orders/{id}")
    public void deleteOrder() {
    }


    @PostMapping(value = "/orders/final/{id}")
    public void finalizeOrder(@PathVariable(required = true) long id) {
        try {
            orderService.finalizeOrder(id);
        } catch (OrderException e) {
            throw new ResponseStatusException(
                    HttpStatus.ACCEPTED, e.getMessage());
        }
    }

}