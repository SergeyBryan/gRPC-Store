package com.sbryan.grpcstore.activeMQ;

import com.sbryan.grpcstore.activeMQ.model.DeliveryActive;
import com.sbryan.grpcstore.zeebe.ZeebeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Component responsible for consuming messages from a JMS (ActiveMQ) destination.
 * It listens to the configured destination and processes incoming messages using the {@link ZeebeService}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryMessageConsumer {

    private final ZeebeService zeebeService;

    /**
     * Listens to the configured JMS destination and processes incoming {@link DeliveryActive} messages.
     *
     * @param deliveryActive the message received from the JMS destination
     */
    @JmsListener(destination = "${activemq.destination}", containerFactory = "jmsFactory")
    public void receiveSystem(DeliveryActive deliveryActive) {
        log.info("Consumer> System Received: {}", deliveryActive);
        zeebeService.receiveFromActive(deliveryActive.getDeliveryId());
    }

}
