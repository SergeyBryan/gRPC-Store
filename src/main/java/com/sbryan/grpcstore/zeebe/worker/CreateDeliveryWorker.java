package com.sbryan.grpcstore.zeebe.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbryan.grpcstore.mapper.DeliveryMapper;
import com.sbryan.grpcstore.model.GlobalDelivery;
import com.sbryan.grpcstore.service.DeliveryService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateDeliveryWorker extends AbstractZeebeWorker<String> {

    private final DeliveryMapper deliveryMapper;
    private final DeliveryService deliveryService;

    @Override
    public String process(final JobClient client, final ActivatedJob job) {
        log.info("Received task {} from process {} with id {}", job.getElementId(), job.getBpmnProcessId(),
                job.getProcessInstanceKey());
        var del = job.getVariablesAsMap().get("delivery");
        ObjectMapper mapper = new ObjectMapper();
        var delivery = mapper.convertValue(del, GlobalDelivery.Delivery.class);
        var entity = deliveryService.createDelivery(deliveryMapper.map(delivery));
        log.info("Delivery has been created");
        return entity.getDeliveryId();
    }

    @JobWorker(type = "startDeliveryProcess", name = "create-delivery-in-system", autoComplete = false, timeout = 5L, requestTimeout = 5L)
    @Override
    public void handle(JobClient client, ActivatedJob job) {
        super.handle(client, job);
    }

    @Override
    public Map<String, Object> createResultParameters(String result, ActivatedJob job) {
        return Map.of("deliveryId", result);
    }
}
