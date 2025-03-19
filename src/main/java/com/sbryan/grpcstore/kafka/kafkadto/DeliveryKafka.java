package com.sbryan.grpcstore.kafka.kafkadto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryKafka {

    private String deliveryId;
    private String storeName;
    private String deliveryType;
    private String deliveryStatus;
    private String city;
    private Integer deliveryStock;
    private Integer deliverySum;
    private Float deliveryWeight;

}
