package com.sbryan.grpcstore.mapper;

import com.sbryan.grpcstore.DeliveryOuterClass;
import com.sbryan.grpcstore.elasticsearch.model.DeliveryEntry;
import com.sbryan.grpcstore.model.GlobalDelivery;
import com.sbryan.grpcstore.rabbitMQ.model.DeliveryRabbit;
import com.sbryan.grpcstore.service.model.DeliveryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting delivery-related objects between different representations.
 * Uses MapStruct with the Spring component model for dependency injection.
 */
@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    GlobalDelivery.Delivery map(DeliveryOuterClass.Delivery delivery);

    DeliveryEntity map(GlobalDelivery.Delivery dto);

    @Mapping(target = "driverEntry", source = "driverRabbit")
    DeliveryEntry map(DeliveryRabbit source);

}
