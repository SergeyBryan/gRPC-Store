package com.sbryan.grpcstore.activeMQ.configuration;

import jakarta.jms.ConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfiguration {

    /**
     * Creates and configures a {@link JmsListenerContainerFactory} bean for JMS message listeners.
     * This factory is used to create containers for consuming JMS messages. It configures the factory
     * with the provided {@link ConnectionFactory} and applies the settings from the
     * {@link DefaultJmsListenerContainerFactoryConfigurer}. Additionally, it sets a custom
     * {@link MessageConverter} for serializing and deserializing messages.
     *
     * @param connectionFactory the JMS {@link ConnectionFactory} used to establish connections to the JMS broker
     * @param configurer        the {@link DefaultJmsListenerContainerFactoryConfigurer} used to apply default configurations
     *                          to the factory
     * @return a configured {@link JmsListenerContainerFactory} instance
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsFactory(ConnectionFactory connectionFactory,
                                                     DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(jacksonJmsMessageConverter());
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    /**
     * Creates a {@link MessageConverter} bean for converting JMS messages to and from JSON format.
     * This converter uses the {@link MappingJackson2MessageConverter} to serialize and deserialize
     * message content as JSON. The target message type is set to {@link MessageType#TEXT}, and a
     * type ID property name is specified to identify the message type during deserialization.
     *
     * @return a configured {@link MessageConverter} instance for JSON message conversion
     */
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_asb_");
        return converter;
    }

}