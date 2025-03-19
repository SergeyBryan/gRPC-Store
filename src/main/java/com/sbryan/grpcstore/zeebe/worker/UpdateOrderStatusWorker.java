package com.sbryan.grpcstore.zeebe.worker;

import com.sbryan.grpcstore.service.DeliveryService;
import com.sbryan.grpcstore.service.DriverService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UpdateOrderStatusWorker extends AbstractZeebeWorker<String> {

    private final DeliveryService deliveryService;
    private final DriverService driverService;

    @JobWorker(type = "updateOrderStatus", name = "updateOrderStatus", autoComplete = false, fetchAllVariables = true, timeout = 5L, requestTimeout = 5L)
    @Override
    public void handle(final JobClient client, final ActivatedJob job) {
        super.handle(client, job);
    }

    @Override
    public String process(JobClient client, ActivatedJob job) {
        var deliveryId = (String) job.getVariablesAsMap().get("deliveryId");
        var delivery = deliveryService.getDeliveryById(deliveryId);
        delivery.setDeliveryStatus("DELIVERED");
        deliveryService.updateDelivery(delivery);
        driverService.lockDriver(delivery.getDriverId(), false);
        return deliveryId;
    }

    @Override
    public Map<String, Object> createResultParameters(String result, ActivatedJob job) {
        return Map.of("deliveryId", getParameterValue("deliveryId", job));
    }

}
