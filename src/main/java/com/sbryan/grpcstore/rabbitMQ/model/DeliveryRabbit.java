package com.sbryan.grpcstore.rabbitMQ.model;

import lombok.Data;

@Data
public class DeliveryRabbit {

    private String deliveryId;
    private String storeName;
    private String deliveryType;
    private String deliveryStatus;
    private String city;
    private Integer deliveryStock;
    private Float deliverySum;
    private Float deliveryWeight;
    private DriverRabbit driverRabbit;

}
