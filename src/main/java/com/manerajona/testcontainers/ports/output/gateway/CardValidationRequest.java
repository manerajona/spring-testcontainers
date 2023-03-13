package com.manerajona.testcontainers.ports.output.gateway;

public record CardValidationRequest(String number, int expDate, int cvc) {
}