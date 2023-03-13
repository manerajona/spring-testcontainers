package com.manerajona.testcontainers.core.domain;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;

public record Payment(BigDecimal amount,
                      PaymentMethod paymentMethod,
                      @Nullable CardDetails card) {

    public Payment(BigDecimal amount, PaymentMethod paymentMethod) {
        this(amount, paymentMethod, null);
    }
}