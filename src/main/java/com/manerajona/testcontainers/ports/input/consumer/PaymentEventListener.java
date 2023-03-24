package com.manerajona.testcontainers.ports.input.consumer;

import com.manerajona.testcontainers.config.RabbitMQConfig;
import com.manerajona.testcontainers.core.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    private static final Logger log = LoggerFactory.getLogger(PaymentEventListener.class);

    private final PaymentService paymentService;

    public PaymentEventListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = {RabbitMQConfig.PAYMENT_EVENTS_QUEUE})
    public void onPaymentEvent(PaymentEvent event) {
        try {
            paymentService.registerPayment(event.payment());
        } catch (Exception e) {
            log.warn("There was an error on event {}", event);
            log.error(e.getMessage(), e);
        }
    }
}