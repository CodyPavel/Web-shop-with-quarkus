package org.service;

import lombok.extern.slf4j.Slf4j;
import org.dto.CustomerDto;
import org.dto.OrderDto;
import org.dto.OrderItemDto;
import org.dto.PaymentDto;
import org.enam.OrderStatus;
import org.enam.PaymentStatus;
import org.entity.*;
import org.repo.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.entity.Order;

@Slf4j
@ApplicationScoped
@Transactional
public class PaymentService {
    @Inject
    PaymentRepository paymentRepository;
    @Inject
    OrderRepo orderRepository;

    public List<PaymentDto> findByPriceRange(Double max) {
        return this.paymentRepository
                .findAllByAmountBetween(BigDecimal.ZERO, BigDecimal.
                        valueOf(max))
                .stream().map(payment -> mapToDto(payment,
                        findOrderByPaymentId(payment.getId()).
                                getId()))
                .collect(Collectors.toList());
    }

    public List<PaymentDto> findAll() {
        return this.paymentRepository.findAll().stream()
                .map(payment -> findById(payment.getId())).
                collect(Collectors.toList());
    }

    public PaymentDto findById(Long id) {
        log.debug("Request to get Payment : {}", id);
        Order order = findOrderByPaymentId(id);
        return this.paymentRepository.findById(id)
                .map(payment -> mapToDto(payment, order.getId())).
                orElse(null);
    }

    public PaymentDto create(PaymentDto paymentDto) {
        log.debug("Request to create Payment : {}", paymentDto);
        Order order = this.orderRepository.findById(paymentDto.getOrderId())
                .orElseThrow(() ->
                        new IllegalStateException("The Order does not exist!"));
        order.setStatus(OrderStatus.PAID);
        Payment payment = this.paymentRepository.saveAndFlush(new Payment(
                paymentDto.getPaypalPaymentId(),
                PaymentStatus.valueOf(paymentDto.getStatus()),
                order.getPrice()
        ));
        this.orderRepository.saveAndFlush(order);
        return mapToDto(payment, order.getId());
    }

    private Order findOrderByPaymentId(Long id) {
        return this.orderRepository.findByPaymentId(id).orElseThrow(() ->
                new IllegalStateException("No Order exists for thePayment ID " + id));
    }

    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        this.paymentRepository.deleteById(id);
    }

    public static PaymentDto mapToDto(Payment payment, Long orderId) {
        if (payment != null) {
            return new PaymentDto(
                    payment.getId(),
                    payment.getPaypalPaymentId(),
                    payment.getStatus().name(),
                    orderId);
        }
        return null;
    }
}