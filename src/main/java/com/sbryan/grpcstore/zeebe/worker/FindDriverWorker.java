package com.sbryan.grpcstore.zeebe.worker;

import com.sbryan.grpcstore.model.DeliveryStatus;
import com.sbryan.grpcstore.service.DeliveryService;
import com.sbryan.grpcstore.service.DriverService;
import com.sbryan.grpcstore.service.model.DriverEntity;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FindDriverWorker extends AbstractZeebeWorker<DeliveryStatus> {

    private final DriverService driverService;
    private final DeliveryService deliveryService;

    @JobWorker(type = "findOrderJob", name = "findOrder", autoComplete = false, fetchAllVariables = true, timeout = 5L, requestTimeout = 5L)
    @Override
    public void handle(final JobClient client, final ActivatedJob job) {
        super.handle(client, job);
    }

    @Override
    public DeliveryStatus process(JobClient client, ActivatedJob job) {
        List<DriverEntity> driver = driverService.getAllByActive(false);

        if (driver.isEmpty()) {
            throw new IllegalStateException("Free driver not found, try later");
        }
        String deliveryId = (String) job.getVariablesAsMap().get("deliveryId");
        String driverId = driver.get(0).getId();
        var delivery = deliveryService.updateDeliveryDriver(deliveryId, driverId);
        driverService.lockDriver(driverId, true);

        return new DeliveryStatus(delivery.getDeliveryId(), delivery.getDeliveryType());
    }

    @Override
    public Map<String, Object> createResultParameters(DeliveryStatus result, ActivatedJob job) {
        return Map.of("deliveryId", result.getDeliveryId(),
                "deliveryStatus", result.getDeliveryStatus());
    }
}
