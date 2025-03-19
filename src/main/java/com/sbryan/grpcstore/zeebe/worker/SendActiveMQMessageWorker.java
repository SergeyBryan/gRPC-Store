package com.sbryan.grpcstore.zeebe.worker;

import com.sbryan.grpcstore.activeMQ.DeliveryMessageProducer;
import com.sbryan.grpcstore.activeMQ.mapper.ActiveMapper;
import com.sbryan.grpcstore.service.DeliveryService;
import com.sbryan.grpcstore.service.DriverService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendActiveMQMessageWorker extends AbstractZeebeWorker<String> {

    private final DeliveryMessageProducer deliveryMessageProducer;
    private final DeliveryService deliveryService;
    private final DriverService driverService;
    private final ActiveMapper mapper;
    private final ActiveMapper activeMapper;

    @Value("${activemq.destination}")
    private String destination;

    @JobWorker(type = "sendMessageToActive", autoComplete = false)
    @Override
    public void handle(JobClient client, ActivatedJob job) {
        super.handle(client, job);
    }

    @Override
    public String process(JobClient client, ActivatedJob job) {
        var deliveryId = (String) job.getVariablesAsMap().get("deliveryId");
        var delivery = deliveryService.getDeliveryById(deliveryId);
        var activeDelivery = activeMapper.map(delivery);
        activeDelivery.setDeliveryEntry(mapper.map(driverService.getDriverById(delivery.getDriverId())));

        deliveryMessageProducer.sendTo(destination, activeDelivery);
        log.info("Successfully sent message to retail: deliveryId={}", deliveryId);
        return deliveryId;
    }

    @Override
    public Map<String, Object> createResultParameters(String result, ActivatedJob job) {
        return Map.of("deliveryId", result);
    }
}
