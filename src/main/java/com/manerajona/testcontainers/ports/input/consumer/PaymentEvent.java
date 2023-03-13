package com.manerajona.testcontainers.ports.input.consumer;

import com.manerajona.testcontainers.core.domain.Payment;

public record PaymentEvent(Payment payment) {
}