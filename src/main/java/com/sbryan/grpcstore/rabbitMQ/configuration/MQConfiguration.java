package com.sbryan.grpcstore.rabbitMQ.configuration;

import com.sbryan.grpcstore.utils.PropertiesConfig;
import com.sbryan.grpcstore.zeebe.ZeebeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up RabbitMQ queues, exchanges, and bindings.
 * It also includes a listener for consuming messages from a RabbitMQ queue.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MQConfiguration {

    private final PropertiesConfig propertiesConfig;
    private final ZeebeService service;

    /**
     * Creates a RabbitMQ queue with the configured name.
     *
     * @return the configured {@link Queue} instance
     */
    @Bean
    public Queue mySystem() {
        return new Queue(propertiesConfig.getRabbit().getQueueName(), true);
    }

    /**
     * Creates a RabbitMQ topic exchange with the configured name.
     *
     * @return the configured {@link TopicExchange} instance
     */
    @Bean
    public Exchange exchange() {
        return new TopicExchange(propertiesConfig.getRabbit().getExchangeName());
    }

    /**
     * Binds the queue to the exchange with the configured routing key.
     *
     * @param queue    the queue to bind
     * @param exchange the exchange to bind to
     * @return the configured {@link Binding} instance
     */
    @Bean
    public Binding bindingMySystem(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(propertiesConfig.getRabbit().getRoutingKey())
                .noargs();
    }

    /**
     * Listens to messages from the configured RabbitMQ queue and processes them using the {@link ZeebeService}.
     *
     * @param message the message received from the RabbitMQ queue
     */
    @RabbitListener(queues = "mySystem")
    public void listenFromMySystem(String message) {
        log.info("Received message RabbitMQ: {} from {}", message, propertiesConfig.getRabbit().getQueueName());
        service.receiveFromRabbit(message);
    }

}
