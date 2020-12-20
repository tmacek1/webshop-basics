package com.assignment.webshop.basics.repository;

import com.assignment.webshop.basics.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM webshop_order_item WHERE id= ?1", nativeQuery = true)
    void deleteOrderItemById(long id);

}
