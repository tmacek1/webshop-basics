package com.assignment.webshop.basics.service;

import com.assignment.webshop.basics.exception.OrderItemException;
import com.assignment.webshop.basics.model.Order;
import com.assignment.webshop.basics.model.OrderItem;
import com.assignment.webshop.basics.model.Product;
import com.assignment.webshop.basics.repository.OrderItemRepository;
import com.assignment.webshop.basics.repository.OrderRepository;
import com.assignment.webshop.basics.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;


    public OrderItem createOrderItem(OrderItem orderItem) throws OrderItemException {

        Optional<Order> order = orderRepository.findById(orderItem.getOrder().getId());
        Optional<Product> product = productRepository.findById(orderItem.getProduct().getId());

        if (!order.isPresent()) {
            throw new OrderItemException("no such order");
        }

        if (!product.isPresent()) {
            throw new OrderItemException("no such product");
        }
        if (!product.get().isAvailable()) {
            throw new OrderItemException("product not available");
        }

        orderItem.setOrder(order.get());
        orderItem.setProduct(product.get());
        orderItem.setQuantity(orderItem.getQuantity());
        return orderItemRepository.save(orderItem);
    }


    public void deleteOrderItem(long id) {

        Optional<OrderItem> fetchOrderItem = orderItemRepository.findById(id);
        if (fetchOrderItem.isPresent()) {
            orderItemRepository.deleteOrderItemById(fetchOrderItem.get().getId());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "order item id not found"
            );

        }
    }
}

