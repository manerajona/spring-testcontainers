package com.manerajona.testcontainers.ports.output.gateway;

import com.manerajona.testcontainers.CardServiceTestContainer;
import com.manerajona.testcontainers.config.WebClientConfig;
import com.manerajona.testcontainers.core.domain.CardDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {CardServiceProxy.class, WebClientConfig.class})
class CardServiceProxyIntegrationTest implements CardServiceTestContainer {

    @Autowired
    CardServiceProxy proxy;

    @BeforeEach
    void check() {
        assertThat(container.isRunning()).isTrue();
    }

    @Test
    void validateCard_test() {
        boolean isValid = proxy.validateCard(
                new CardDetails("1234567890123456", 1129, 123)
        );
        assertThat(isValid).isTrue();
    }
}