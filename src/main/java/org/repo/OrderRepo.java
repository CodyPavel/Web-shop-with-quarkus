package org.repo;

import org.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByCartCustomerId(Long customerId);
    Optional<Order> findByPaymentId(Long id);
}