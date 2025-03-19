package com.sbryan.grpcstore.service.impl;

import com.sbryan.grpcstore.service.DeliveryService;
import com.sbryan.grpcstore.service.model.DeliveryEntity;
import com.sbryan.grpcstore.service.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {


    private final DeliveryRepository deliveryRepository;

    @Override
    public DeliveryEntity createDelivery(DeliveryEntity entity) {
        AtomicInteger id = new AtomicInteger(deliveryRepository.findTopByDeliveryId());
        entity.setDeliveryId(String.valueOf(id.incrementAndGet()));
        return deliveryRepository.save(entity);
    }

    @Override
    public DeliveryEntity updateDelivery(DeliveryEntity entity) {
        return deliveryRepository.save(entity);
    }

    @Override
    public DeliveryEntity updateDeliveryDriver(String deliveryId, String driverId) {
        var delivery = getDeliveryById(deliveryId);
        delivery.setDriverId(driverId);
        return deliveryRepository.save(delivery);
    }

    @Override
    public DeliveryEntity getDeliveryById(String id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Delivery not found %s".formatted(id)));
    }


}