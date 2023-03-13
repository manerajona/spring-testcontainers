CREATE SCHEMA IF NOT EXISTS payment_service;

CREATE TABLE payment_service.payment
(
    id             UUID PRIMARY KEY,
    amount         DOUBLE PRECISION,
    payment_method VARCHAR(10),
    payment_status VARCHAR(20),
    payment_date   TIMESTAMP
);