package com.manerajona.testcontainers.core.usecase;

import com.manerajona.testcontainers.core.PaymentService;
import com.manerajona.testcontainers.core.domain.Payment;
import com.manerajona.testcontainers.core.domain.PaymentMethod;
import com.manerajona.testcontainers.core.domain.PaymentStatus;
import com.manerajona.testcontainers.core.repository.CardRepository;
import com.manerajona.testcontainers.core.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CardRepository cardRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, CardRepository cardRepository) {
        this.paymentRepository = paymentRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public void registerPayment(Payment payment) {
        if (Objects.equals(payment.paymentMethod(), PaymentMethod.CARD)) {
            UUID id = paymentRepository.create(payment, PaymentStatus.PENDING_VALIDATION);
            boolean isValidCard = cardRepository.validateCard(payment.card());
            paymentRepository.updateStatus(id, isValidCard ? PaymentStatus.OK : PaymentStatus.ERROR);
        } else {
            paymentRepository.create(payment, PaymentStatus.OK);
        }
    }
}