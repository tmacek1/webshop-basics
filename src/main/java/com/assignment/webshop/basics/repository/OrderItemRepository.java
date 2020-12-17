package com.assignment.webshop.basics.repository;

import com.assignment.webshop.basics.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Transactional
    Optional<List<OrderItem>> findByOrderId(Long id);

}
