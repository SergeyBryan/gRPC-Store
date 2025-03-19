package com.sbryan.grpcstore.zeebe.worker;

import com.sbryan.grpcstore.rabbitMQ.RabbitMapper;
import com.sbryan.grpcstore.service.DeliveryService;
import com.sbryan.grpcstore.service.DriverService;
import com.sbryan.grpcstore.utils.PropertiesConfig;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendRabbitMQMessageWorker extends AbstractZeebeWorker<String> {

    private final RabbitTemplate rabbitTemplate;
    private final PropertiesConfig propertiesConfig;
    private final RabbitMapper rabbitMapper;
    private final DeliveryService deliveryService;
    private final DriverService driverService;

    @JobWorker(type = "sendMessageToRabbit", autoComplete = false)
    @Override
    public void handle(JobClient client, ActivatedJob job) {
        super.handle(client, job);
    }

    @Override
    public String process(JobClient client, ActivatedJob job) {
        var deliveryId = (String) job.getVariable("deliveryId");
        var delivery = deliveryService.getDeliveryById(deliveryId);
        var deliveryRabbit = rabbitMapper.map(delivery);

        deliveryRabbit.setDriverRabbit(rabbitMapper.map(driverService.getDriverById(delivery.getDriverId())));

        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        rabbitTemplate.convertAndSend(propertiesConfig.getRabbit().getExchangeName(),
                propertiesConfig.getRabbit().getRoutingKey(),
                deliveryRabbit);
        log.info("The message successfully sent");
        return deliveryId;
    }

    @Override
    public Map<String, Object> createResultParameters(String result, ActivatedJob job) {
        return Map.of("deliveryId", result);
    }
}
