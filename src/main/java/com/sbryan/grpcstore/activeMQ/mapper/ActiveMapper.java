package com.sbryan.grpcstore.activeMQ.mapper;

import com.sbryan.grpcstore.activeMQ.model.DeliveryActive;
import com.sbryan.grpcstore.activeMQ.model.DriverActive;
import com.sbryan.grpcstore.service.model.DeliveryEntity;
import com.sbryan.grpcstore.service.model.DriverEntity;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting entities to active domain models.
 * Uses MapStruct with the Spring component model for dependency injection.
 */
@Mapper(componentModel = "spring")
public interface ActiveMapper {

    DeliveryActive map(DeliveryEntity source);

    DriverActive map(DriverEntity entity);

}
