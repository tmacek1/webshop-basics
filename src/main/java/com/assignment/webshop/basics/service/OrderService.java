package com.assignment.webshop.basics.service;

import com.assignment.webshop.basics.client.HnbRestClient;
import com.assignment.webshop.basics.entity.Customer;
import com.assignment.webshop.basics.entity.Order;
import com.assignment.webshop.basics.entity.OrderItem;
import com.assignment.webshop.basics.exception.OrderException;
import com.assignment.webshop.basics.exception.OrderItemException;
import com.assignment.webshop.basics.model.HnbDTO;
import com.assignment.webshop.basics.repository.CustomerRepository;
import com.assignment.webshop.basics.repository.OrderItemRepository;
import com.assignment.webshop.basics.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    HnbRestClient hnbRestClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(long id) {

        Optional<Order> fetchOrder = orderRepository.findById(id);
        if (fetchOrder.isPresent()) {
            BigDecimal totalPriceHrk = new BigDecimal(0);
            Order getOrder = fetchOrder.get();
            if (!getOrder.getOrderItems().isEmpty()) {
                for (OrderItem orderItem : getOrder.getOrderItems()) {
                    BigDecimal quantityMultipler = orderItem.getProduct().getPriceHrk().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                    totalPriceHrk = totalPriceHrk.add(quantityMultipler);
                }
            }
            getOrder.setTotalPriceHrk(totalPriceHrk);
            return Optional.of(getOrder);
        }

        return Optional.empty();
    }

    public Optional<Order> createOrder(Order order) {

        Optional<Customer> fetchCustomer = customerRepository.findById(order.getCustomer().getId());
        if (fetchCustomer.isPresent()) {

            order.setCustomer(fetchCustomer.get());
            order.setStatus(Order.Status.DRAFT);
            orderRepository.save(order);
            return Optional.of(order);
        }

        return Optional.empty();
    }

    public Optional<Order> updateOrder(Order order) {

        Optional<Order> fetchOrder = orderRepository.findById(order.getId());
        Optional<Customer> fetchCustomer = customerRepository.findById(order.getCustomer().getId());

        if (fetchOrder.isPresent() && fetchCustomer.isPresent()) {

            Order targetOrder = fetchOrder.get();

            // handle orderItem??

            targetOrder.setCustomer(fetchCustomer.get());
            orderRepository.save(targetOrder);
            return Optional.of(targetOrder);
        }

        orderRepository.save(order);
        return Optional.of(order);
    }


    public void finalizeOrder(long id) throws OrderException {

        Optional<Order> targetOrder = getOrderById(id);
        if (targetOrder.isPresent()) {

            Order result = targetOrder.get();

            ResponseEntity<HnbDTO[]> fetchCurrentEuro = hnbRestClient.fetchCurrencyInformation();

            if (fetchCurrentEuro.getStatusCode() == HttpStatus.OK) {
                BigDecimal currentEuro = new BigDecimal(0);

                for (HnbDTO hnbDTO : fetchCurrentEuro.getBody()) {
                    String prepareString = hnbDTO.getProdajniZaDevize().replace(',', '.');
                    currentEuro = new BigDecimal(prepareString);
                }

                if (result.getTotalPriceHrk().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal totalPriceEur = result.getTotalPriceHrk().divide(currentEuro, 2, RoundingMode.HALF_UP);
                    result.setTotalPriceEur(totalPriceEur);
                    result.setStatus(Order.Status.SUBMITTED);
                }

                orderRepository.save(result);

            } else {

                throw new OrderException("error while fetching from hnb api");

            }

        }

    }
}
