package com.sbryan.grpcstore.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "grpcstore")
public class PropertiesConfig {

    private Kafka kafka;
    private Rabbit rabbit;

    @Data
    public static class Kafka {
        private String servers;
        private String topicName;
        private String topicName2;
        private String topicName3;
        private String deliveryProcess;
    }

    @Data
    public static class Rabbit {
        private String queueName;
        private String exchangeName;
        private String routingKey;
    }

}
