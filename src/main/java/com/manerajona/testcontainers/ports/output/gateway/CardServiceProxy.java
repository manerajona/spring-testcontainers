package com.manerajona.testcontainers.ports.output.gateway;

import com.manerajona.testcontainers.config.CardProxyProperties;
import com.manerajona.testcontainers.core.domain.CardDetails;
import com.manerajona.testcontainers.core.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@EnableConfigurationProperties(CardProxyProperties.class)
public class CardServiceProxy implements CardRepository {

    private static final Logger log = LoggerFactory.getLogger(CardServiceProxy.class);

    private final WebClient client;
    private final CardProxyProperties properties;

    public CardServiceProxy(WebClient client, CardProxyProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @Override
    public boolean validateCard(CardDetails card) {
        try {
            CardValidationRequest request = new CardValidationRequest(
                    card.number(),
                    card.expDate(),
                    card.cvc()
            );

            return client.post()
                    .uri(properties.validateUri())
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .toEntity(CardValidationResponse.class)
                    .map(response -> response.getBody().isValid())
                    .block();

        } catch (Exception e) {
            log.warn("There was an error calling the card service");
            log.error(e.getMessage(), e);
        }
        return false;
    }
}