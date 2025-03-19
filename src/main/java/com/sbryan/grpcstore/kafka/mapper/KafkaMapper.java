package com.sbryan.grpcstore.kafka.mapper;

import com.sbryan.grpcstore.kafka.kafkadto.DeliveryKafka;
import com.sbryan.grpcstore.service.model.DeliveryEntity;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting entities to Kafka-specific objects and vice versa.
 * Uses MapStruct with the Spring component model for dependency injection.
 */
@Mapper(componentModel = "spring")
public interface KafkaMapper {

    DeliveryKafka map(DeliveryEntity source);

    DeliveryEntity map(DeliveryKafka source);
}