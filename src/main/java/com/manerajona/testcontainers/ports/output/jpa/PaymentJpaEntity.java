package com.manerajona.testcontainers.ports.output.jpa;

import com.manerajona.testcontainers.core.domain.PaymentMethod;
import com.manerajona.testcontainers.core.domain.PaymentStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "payment_service", name = "payment")
@Access(AccessType.FIELD)
public class PaymentJpaEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @CreationTimestamp
    private Instant paymentDate;

    public PaymentJpaEntity(BigDecimal amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public PaymentJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentJpaEntity that = (PaymentJpaEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "payment(" +
                "id=" + id +
                ", amount=" + amount +
                ", payment_method=" + paymentMethod +
                ", payment_status=" + paymentStatus +
                ", payment_date=" + paymentDate +
                ')';
    }
}