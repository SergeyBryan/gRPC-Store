package com.sbryan.grpcstore.zeebe.worker;

import com.sbryan.grpcstore.elasticsearch.DeliveryElasticService;
import com.sbryan.grpcstore.mapper.DeliveryMapper;
import com.sbryan.grpcstore.rabbitMQ.model.DeliveryRabbit;
import com.sbryan.grpcstore.rabbitMQ.model.DriverRabbit;
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
public class UpdateDeliveryIndexWorker extends AbstractZeebeWorker<String> {

    private final DeliveryElasticService deliveryIndex;
    private final DeliveryMapper mapper;

    @JobWorker(type = "updateDeliveryIndex", autoComplete = false, fetchAllVariables = true)
    @Override
    public void handle(JobClient client, ActivatedJob job) {
        super.handle(client, job);
    }

    @Override
    public String process(JobClient client, ActivatedJob job) {
        var deliveryMap = (Map<String, Object>) job.getVariablesAsMap().get("delivery");
        var delivery = map(deliveryMap);
        var deliveryEntry = mapper.map(delivery);

        return deliveryIndex.saveDeliveryEntry(deliveryEntry);
    }

    @Override
    public Map<String, Object> createResultParameters(String result, ActivatedJob job) {
        return Map.of("deliveryId", getParameterValue("deliveryId", job));
    }

    private DeliveryRabbit map(Map<String, Object> source) {
        DeliveryRabbit result = new DeliveryRabbit();
        result.setDeliveryId(getStringValue(source, "deliveryId"));
        result.setCity(getStringValue(source, "city"));
        result.setStoreName(getStringValue(source, "storeName"));
        result.setDeliveryStatus(getStringValue(source, "deliveryStatus"));
        result.setDeliveryType(getStringValue(source, "deliveryType"));
        result.setDeliverySum(getFloatValue(source, "deliverySum"));
        result.setDeliveryStock(getIntegerValue(source, "deliveryStock"));
        result.setDeliveryWeight(getFloatValue(source, "deliveryWeight"));
        DriverRabbit driver = new DriverRabbit();
        var sourceDriver = mapDriver(source, "driverRabbit");
        if (sourceDriver != null) {
            driver.setId(getStringValue(sourceDriver, "id"));
            driver.setName(getStringValue(sourceDriver, "name"));
            driver.setAge(getIntegerValue(sourceDriver, "age"));
            driver.setPhoneNumber(getStringValue(sourceDriver, "phoneNumber"));
            driver.setActive(getBooleanValue(sourceDriver, "active"));
            result.setDriverRabbit(driver);
        }
        return result;
    }

    private Map<String, Object> mapDriver(Map<String, Object> source, String key) {
        var value = source.get(key);
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        return null;
    }

    private Float getFloatValue(Map<String, Object> map, String key) {
        var value = map.get(key);
        if (value != null) {
            return Float.parseFloat(value.toString());
        }
        return null;
    }

    private String getStringValue(Map<String, Object> map, String key) {
        var value = map.get(key);
        if (value != null) {
            return value.toString();
        }
        return null;
    }

    private Integer getIntegerValue(Map<String, Object> map, String key) {
        var value = map.get(key);
        if (value != null) {
            return Integer.parseInt(value.toString());
        }
        return null;
    }

    private Boolean getBooleanValue(Map<String, Object> map, String key) {
        var value = map.get(key);
        if (value != null) {
            return Boolean.parseBoolean(value.toString());
        }
        return null;
    }

}
