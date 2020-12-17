package com.assignment.webshop.basics.service;

import com.assignment.webshop.basics.entity.Order;
import com.assignment.webshop.basics.entity.OrderItem;
import com.assignment.webshop.basics.entity.Product;
import com.assignment.webshop.basics.exception.OrderItemException;
import com.assignment.webshop.basics.repository.OrderItemRepository;
import com.assignment.webshop.basics.repository.OrderRepository;
import com.assignment.webshop.basics.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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


}
