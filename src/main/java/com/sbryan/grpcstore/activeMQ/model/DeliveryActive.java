package com.sbryan.grpcstore.activeMQ.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeliveryActive {

    private String deliveryId;
    private String storeName;
    private String city;
    private String deliveryStatus;
    private String deliveryType;
    private Integer deliveryStock;
    private Integer deliverySum;
    private Float deliveryWeight;
    private DriverActive deliveryEntry;

}
