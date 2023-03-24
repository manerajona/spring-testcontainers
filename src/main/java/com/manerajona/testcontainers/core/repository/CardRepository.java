package com.manerajona.testcontainers.core.repository;

import com.manerajona.testcontainers.core.domain.CardDetails;

public interface CardRepository {
    boolean validateCard(CardDetails card);
}