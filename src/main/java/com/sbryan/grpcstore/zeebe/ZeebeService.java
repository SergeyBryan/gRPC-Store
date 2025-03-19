package com.sbryan.grpcstore.zeebe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbryan.grpcstore.kafka.kafkadto.DeliveryKafka;
import com.sbryan.grpcstore.model.GlobalDelivery;
import com.sbryan.grpcstore.rabbitMQ.model.DeliveryRabbit;
import com.sbryan.grpcstore.utils.ConverterUtils;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.ZeebeFuture;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
@RequiredArgsConstructor
//@Deployment(resources = "classpath*:/bpmn/**/*.bpmn")
public class ZeebeService {

    private final ZeebeClient zeebeClient;

    public ZeebeFuture<ProcessInstanceEvent> startProcess(String processId, GlobalDelivery delivery) {
        return zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(processId)
                .latestVersion()
                .variables(delivery)
                .send();
    }

    public void receiveFromKafka(String message) {
        String deliveryId = ConverterUtils.readJsonAsString(message, DeliveryKafka.class).getDeliveryId();
        zeebeClient.newPublishMessageCommand()
                .messageName("kafkaMessage")
                .correlationKey(deliveryId)
                .variables(Map.of("deliveryId", deliveryId))
                .messageId("Сообщение из кафки")
                .timeToLive(Duration.of(1L, ChronoUnit.SECONDS))
                .send();
    }

    public void receiveFromRabbit(String message) {
        var delivery = ConverterUtils.readJsonAsString(message, DeliveryRabbit.class);
        zeebeClient.newPublishMessageCommand()
                .messageName("rabbitMessage")
                .correlationKey(delivery.getDeliveryId())
                .variables(Map.of("delivery", delivery))
                .messageId("Сообщение из rabbit")
                .timeToLive(Duration.of(1L, ChronoUnit.SECONDS))
                .send();
    }

    public void receiveFromActive(String deliveryId) {
        zeebeClient.newPublishMessageCommand()
                .messageName("activeMessage")
                .correlationKey(deliveryId)
                .messageId("Сообщение из active")
                .timeToLive(Duration.of(1L, ChronoUnit.SECONDS))
                .send();
    }


}