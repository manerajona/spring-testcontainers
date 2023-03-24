package com.manerajona.testcontainers.core.repository;

import com.manerajona.testcontainers.core.domain.Payment;
import com.manerajona.testcontainers.core.domain.PaymentStatus;

import java.util.UUID;

public interface PaymentRepository {
    UUID create(Payment payment, PaymentStatus status);

    boolean updateStatus(UUID id, PaymentStatus status);
}