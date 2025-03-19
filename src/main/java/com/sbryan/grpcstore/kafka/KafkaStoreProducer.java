package com.sbryan.grpcstore.kafka;

import com.sbryan.grpcstore.kafka.kafkadto.DeliveryKafka;
import com.sbryan.grpcstore.kafka.mapper.KafkaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for producing messages to Kafka topics.
 * Uses a {@link KafkaTemplate} to send messages and a {@link KafkaMapper} to convert messages if needed.
 */
@Service
@RequiredArgsConstructor
public class KafkaStoreProducer {
    private final KafkaMapper kafkaMapper;
    private final KafkaTemplate<String, Object> messageKafkaTemplate;

    /**
     * Sends a message to the specified Kafka topic.
     *
     * @param topic   the Kafka topic to send the message to
     * @param message the message to send
     * @param <T>     the type of the message
     */
    public <T> void sendMessage(String topic, T message) {
        messageKafkaTemplate.send(topic, convertIfNeeded(message));
    }

    /**
     * Converts the message to the appropriate format if necessary.
     *
     * @param message the message to convert
     * @param <T>     the type of the message
     * @return the converted message
     */
    private <T> Object convertIfNeeded(T message) {
        if (message instanceof DeliveryKafka) {
            return kafkaMapper.map((DeliveryKafka) message);
        }
        return message;
    }
}
