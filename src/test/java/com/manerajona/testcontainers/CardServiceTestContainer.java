package com.manerajona.testcontainers;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
public interface CardServiceTestContainer {

    String DOCKER_IMAGE_NAME = "manerajona/card-service";
    int PORT = 8080;

    @Container
    GenericContainer<?> container = new GenericContainer<>(DOCKER_IMAGE_NAME)
            .withExposedPorts(PORT);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("ws.card.base-url",
                () -> String.format("http://localhost:%d", container.getMappedPort(PORT)));
    }
}