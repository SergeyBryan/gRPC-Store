package com.sbryan.grpcstore.service;

import com.sbryan.grpcstore.service.model.DeliveryEntity;

public interface DeliveryService {

    DeliveryEntity createDelivery(DeliveryEntity entity);

    DeliveryEntity updateDelivery(DeliveryEntity entity);

    DeliveryEntity updateDeliveryDriver(String deliveryId, String driverId);

    DeliveryEntity getDeliveryById(String id);
}
