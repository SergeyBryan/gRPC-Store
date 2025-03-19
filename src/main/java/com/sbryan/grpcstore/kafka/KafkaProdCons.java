package com.sbryan.grpcstore.kafka;

import com.sbryan.grpcstore.kafka.kafkadto.DeliveryKafka;
import com.sbryan.grpcstore.utils.PropertiesConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

/**
 * Configuration class for setting up Kafka producers and templates.
 * Configures Kafka producer properties and creates KafkaTemplate instances for different message types.
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(PropertiesConfig.class)
public class KafkaProdCons {

    private final PropertiesConfig propertiesConfig;

    /**
     * Configures common Kafka producer properties.
     *
     * @return a map of Kafka producer configuration properties
     */
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        configProps.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return configProps;
    }

    /**
     * Creates a {@link ProducerFactory} for a specific message type.
     *
     * @param clazz the class of the message type
     * @param <T>   the type of the message
     * @return a configured {@link ProducerFactory} instance
     */
    private <T> ProducerFactory<String, T> producerFactory(Class<T> clazz) {
        Map<String, Object> configProps = new HashMap<>(producerConfigs());
        configProps.put(VALUE_SERIALIZER_CLASS_CONFIG, getValue(clazz));
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Determines the appropriate serializer class for the given message type.
     *
     * @param clazz the class of the message type
     * @param <T>   the type of the message
     * @return the serializer class to use
     */
    private <T> Object getValue(Class<T> clazz) {
        return clazz.equals(String.class) ?
                StringSerializer.class : JsonSerializer.class;
    }

    /**
     * Creates a KafkaTemplate for sending String messages.
     *
     * @return a configured {@link KafkaTemplate} instance
     */
    @Bean
    public KafkaTemplate<String, String> messageKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(String.class));
    }

    /**
     * Creates a KafkaTemplate for sending generic Object messages.
     *
     * @return a configured {@link KafkaTemplate} instance
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(Object.class));
    }

    /**
     * Creates a KafkaTemplate for sending {@link DeliveryKafka} messages.
     *
     * @return a configured {@link KafkaTemplate} instance
     */
    @Bean
    public KafkaTemplate<String, DeliveryKafka> deliveryKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(DeliveryKafka.class));
    }

    /**
     * Retrieves the Kafka bootstrap servers from the configuration.
     *
     * @return the bootstrap servers as a comma-separated string
     */
    private String getBootstrapServers() {
        return propertiesConfig.getKafka().getServers();
    }

}
