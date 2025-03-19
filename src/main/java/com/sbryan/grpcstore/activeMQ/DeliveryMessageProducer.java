package com.sbryan.grpcstore.activeMQ;


import com.sbryan.grpcstore.activeMQ.model.DeliveryActive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Component responsible for producing messages to a JMS (ActiveMQ) destination.
 * It sends {@link DeliveryActive} messages to the specified destination.
 */
@Component
@Slf4j
public class DeliveryMessageProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * Converts and sends a {@link DeliveryActive} message to the specified JMS destination.
     *
     * @param destination    the JMS destination to send the message to
     * @param deliveryActive the message to send
     */
    public void sendTo(String destination, DeliveryActive deliveryActive) {
        jmsTemplate.convertAndSend(destination, deliveryActive);
        log.info("Producer> Message Sent");
    }


}
