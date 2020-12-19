package com.assignment.webshop.basics.service;

import com.assignment.webshop.basics.client.HnbRestClient;
import com.assignment.webshop.basics.model.Customer;
import com.assignment.webshop.basics.model.Order;
import com.assignment.webshop.basics.model.OrderItem;
import com.assignment.webshop.basics.exception.OrderException;
import com.assignment.webshop.basics.dto.HnbDTO;
import com.assignment.webshop.basics.repository.CustomerRepository;
import com.assignment.webshop.basics.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    HnbRestClient hnbRestClient;

    public Optional<Order> getOrderById(long id) {

        Optional<Order> fetchOrder = orderRepository.findById(id);
        if (fetchOrder.isPresent()) {
            Order getOrder = fetchOrder.get();
            return Optional.of(getOrder);
        }

        return Optional.empty();
    }

    public Order createOrder(long id) throws OrderException {
        Order order = new Order();
        order.setStatus(Order.Status.DRAFT);
        order.setTotalPriceEur(new BigDecimal("0.00"));
        order.setTotalPriceHrk(new BigDecimal("0.00"));
        Optional<Customer> searchCustomer = customerRepository.findById(id);
        if (searchCustomer.isPresent()) {
            Customer targetCustomer = searchCustomer.get();
            order.setCustomer(targetCustomer);
        } else {
            throw new OrderException("customer not found");
        }
        return orderRepository.save(order);
    }

    public Order finalizeOrder(long id) throws OrderException {

        Optional<Order> targetOrder = orderRepository.findById(id);

        if (targetOrder.isPresent()) {
            Order result = targetOrder.get();
            BigDecimal totalPriceHrk = new BigDecimal(0);
            if (!result.getOrderItems().isEmpty()) {
                for (OrderItem orderItem : result.getOrderItems()) {
                    BigDecimal quantityMultipler = orderItem.getProduct().getPriceHrk().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                    totalPriceHrk = totalPriceHrk.add(quantityMultipler);
                }
            }

            result.setTotalPriceHrk(totalPriceHrk);

            if (result.getTotalPriceHrk().compareTo(BigDecimal.ZERO) > 0) {

                ResponseEntity<HnbDTO[]> fetchCurrentEuro = hnbRestClient.fetchCurrencyInformation();

                if (fetchCurrentEuro.getStatusCode() == HttpStatus.OK) {
                    BigDecimal currentEuro = new BigDecimal(0);

                    for (HnbDTO hnbDTO : fetchCurrentEuro.getBody()) {
                        String prepareString = hnbDTO.getProdajniZaDevize().replace(',', '.');
                        currentEuro = new BigDecimal(prepareString);
                    }

                    BigDecimal totalPriceEur = result.getTotalPriceHrk().divide(currentEuro, 2, RoundingMode.HALF_UP);
                    result.setTotalPriceEur(totalPriceEur);
                    result.setStatus(Order.Status.SUBMITTED);

                    return orderRepository.save(result);
                }
            }
        }
        throw new OrderException("error finalizing order");
    }

    public void deleteOrder(long id) {
        Optional<Order> fetchOrder = orderRepository.findById(id);
        if (fetchOrder.isPresent()) {
            orderRepository.deleteById(fetchOrder.get().getId());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "order not found"
            );
        }
    }
}