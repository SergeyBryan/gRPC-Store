package com.sbryan.grpcstore.zeebe.worker;

import com.sbryan.grpcstore.kafka.kafkadto.DeliveryKafka;
import com.sbryan.grpcstore.kafka.mapper.KafkaMapper;
import com.sbryan.grpcstore.service.DeliveryService;
import com.sbryan.grpcstore.utils.PropertiesConfig;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendKafkaMessageWorker extends AbstractZeebeWorker<String> {

    private final DeliveryService deliveryService;
    private final KafkaTemplate<String, DeliveryKafka> deliveryKafkaTemplate;
    private final KafkaMapper kafkaMapper;
    private final PropertiesConfig propertiesConfig;


    @JobWorker(type = "sendMessageToKafka", autoComplete = false, timeout = 1L, requestTimeout = 1L)
    @Override
    public void handle(JobClient client, ActivatedJob job) {
        super.handle(client, job);
    }

    @Override
    public String process(JobClient client, ActivatedJob job) {
        var deliveryId = (String) job.getVariable("deliveryId");
        var delivery = deliveryService.getDeliveryById(deliveryId);
        deliveryKafkaTemplate.send(propertiesConfig.getKafka().getDeliveryProcess(), kafkaMapper.map(delivery));
        log.info("Успешная отправка");
        return deliveryId;
    }

    @Override
    public Map<String, Object> createResultParameters(String result, ActivatedJob job) {
        return Map.of("deliveryId", result);
    }

}
