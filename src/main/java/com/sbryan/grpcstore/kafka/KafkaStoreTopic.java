package com.sbryan.grpcstore.kafka;

import com.sbryan.grpcstore.utils.PropertiesConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Configuration class for creating and managing Kafka topics.
 * Checks if topics already exist before creating them to avoid duplicates.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(PropertiesConfig.class)
public class KafkaStoreTopic {

    private final PropertiesConfig propertiesConfig;

    /**
     * Creates a new Kafka topic with the configured name.
     *
     * @return a {@link NewTopic} instance
     */
    @Bean
    public NewTopic kafkaTopic() {
        return TopicBuilder.name(propertiesConfig.getKafka().getTopicName()).build();
    }

    /**
     * Creates a new Kafka topic with the configured name and 3 partitions.
     * Checks if the topic already exists before creating it.
     *
     * @return a {@link NewTopic} instance, or null if the topic already exists
     * @throws ExecutionException   if the topic existence check fails
     * @throws InterruptedException if the topic existence check is interrupted
     */
    @Bean
    public NewTopic kafkaTopic2() throws ExecutionException, InterruptedException {
        var topic = TopicBuilder.name(propertiesConfig.getKafka().getTopicName2()).partitions(3).build();
        if (exist().contains(topic.name())) {
            log.warn("topic {} exists", topic.name());
            return null;
        }
        return topic;
    }

    /**
     * Creates a new Kafka topic with the configured name.
     * Checks if the topic already exists before creating it.
     *
     * @return a {@link NewTopic} instance, or null if the topic already exists
     * @throws ExecutionException   if the topic existence check fails
     * @throws InterruptedException if the topic existence check is interrupted
     */
    @Bean
    public NewTopic kafkaTopic3() throws ExecutionException, InterruptedException {
        var topic = TopicBuilder.name(propertiesConfig.getKafka().getTopicName3()).build();
        if (exist().contains(topic.name())) {
            log.warn("topic {} exists", topic.name());
            return null;
        }
        return topic;
    }

    /**
     * Creates a new Kafka topic for delivery process messages.
     * Checks if the topic already exists before creating it.
     *
     * @return a {@link NewTopic} instance, or null if the topic already exists
     * @throws ExecutionException   if the topic existence check fails
     * @throws InterruptedException if the topic existence check is interrupted
     */
    @Bean
    public NewTopic kafkaDeliveryProcess() throws ExecutionException, InterruptedException {
        var topic = TopicBuilder.name(propertiesConfig.getKafka().getDeliveryProcess()).build();
        if (exist().contains(topic.name())) {
            log.warn("topic {} exists", topic.name());
            return null;
        }
        return topic;
    }

    /**
     * Retrieves the set of existing Kafka topics.
     *
     * @return a set of existing topic names
     * @throws ExecutionException   if the topic listing fails
     * @throws InterruptedException if the topic listing is interrupted
     */
    public Set<String> exist() throws ExecutionException, InterruptedException {
        AdminClient adminClient = KafkaAdminClient.create(Map.of("bootstrap.servers", propertiesConfig.getKafka().getServers()));
        return adminClient.listTopics().names().get();
    }


}