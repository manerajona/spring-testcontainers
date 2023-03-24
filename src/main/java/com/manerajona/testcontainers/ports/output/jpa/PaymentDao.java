package com.manerajona.testcontainers.ports.output.jpa;

import com.manerajona.testcontainers.core.domain.Payment;
import com.manerajona.testcontainers.core.domain.PaymentStatus;
import com.manerajona.testcontainers.core.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class PaymentDao implements PaymentRepository {

    private static final Logger log = LoggerFactory.getLogger(PaymentDao.class);

    private final PaymentJpaRepository jpaRepository;

    public PaymentDao(PaymentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public UUID create(Payment payment, PaymentStatus status) {
        PaymentJpaEntity paymentCreated = jpaRepository.save(
                new PaymentJpaEntity(payment.amount(), payment.paymentMethod(), status)
        );
        log.debug("Payment created: {}", paymentCreated);
        return paymentCreated.getId();
    }

    @Override
    public boolean updateStatus(UUID paymentId, PaymentStatus status) {
        AtomicBoolean updated = new AtomicBoolean(false);
        jpaRepository.findById(paymentId)
                .ifPresent(payment -> {
                    payment.setPaymentStatus(status);
                    jpaRepository.save(payment);
                    log.debug("Payment updated: {}", payment);
                    updated.set(true);
                });
        return updated.get();
    }
}