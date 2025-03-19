package com.sbryan.grpcstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryStatus {

    private String deliveryId;
    private String deliveryStatus;

}
