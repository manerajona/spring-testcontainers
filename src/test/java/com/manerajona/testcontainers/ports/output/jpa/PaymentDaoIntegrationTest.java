package com.manerajona.testcontainers.ports.output.jpa;

import com.manerajona.testcontainers.PostgresTestContainer;
import com.manerajona.testcontainers.core.domain.Payment;
import com.manerajona.testcontainers.core.domain.PaymentMethod;
import com.manerajona.testcontainers.core.domain.PaymentStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = {"com.manerajona.testcontainers.ports.output.jpa"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentDaoIntegrationTest implements PostgresTestContainer {

    static UUID paymentId;

    @Autowired
    PaymentDao dao;

    @BeforeEach
    void check() {
        assertThat(container.isRunning()).isTrue();
    }

    @Test
    @Order(1)
    @Commit
    void create_test() {
        paymentId = dao.create(
                new Payment(BigDecimal.TEN, PaymentMethod.CARD),
                PaymentStatus.PENDING_VALIDATION
        );

        assertThat(paymentId).isNotNull();
    }

    @Test
    @Order(2)
    void updateStatus_test_withExistingPaymentId() {
        assertThat(dao.updateStatus(paymentId, PaymentStatus.OK)).isTrue();
    }

    @Test
    @Order(3)
    void updateStatus_test_withNonExistingPaymentId() {
        UUID randomPaymentId = UUID.randomUUID();
        assertThat(dao.updateStatus(randomPaymentId, PaymentStatus.OK)).isFalse();
    }
}