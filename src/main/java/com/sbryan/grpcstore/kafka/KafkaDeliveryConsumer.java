package com.sbryan.grpcstore.kafka;

import com.sbryan.grpcstore.zeebe.ZeebeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for consuming messages from Kafka topics.
 * It listens to multiple Kafka topics and processes incoming messages.
 * Uses {@link ZeebeService} to handle messages related to delivery processes.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaDeliveryConsumer {

    private final ZeebeService zeebe;

    /**
     * Listens to multiple Kafka topics and logs the received messages.
     *
     * @param message the message received from the Kafka topic
     */
    @KafkaListener(topics = {
            "${grpcstore.kafka.topic-name}",
            "${grpcstore.kafka.topic-name2}",
            "${grpcstore.kafka.topic-name3}"},
            groupId = "${grpcstore.kafka.group-id}")
    public void listen(String message) {
        log.info("Received message: {}", message);
    }

    /**
     * Listens to a specific Kafka topic for delivery process messages.
     * Logs the received message and forwards it to the {@link ZeebeService} for further processing.
     *
     * @param message the message received from the Kafka topic
     */
    @KafkaListener(topics = {"${grpcstore.kafka.delivery-process}"},
            groupId = "${grpcstore.kafka.group-id}")
    public void receiveMessage(String message) {
        log.info("Received new delivery message: {}", message);
        zeebe.receiveFromKafka(message);
    }
}
