package com.manerajona.testcontainers.core;

import com.manerajona.testcontainers.core.domain.Payment;

public interface PaymentService {
    void registerPayment(Payment payment);
}