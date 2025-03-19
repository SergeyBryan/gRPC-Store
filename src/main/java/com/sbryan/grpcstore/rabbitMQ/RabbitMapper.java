package com.sbryan.grpcstore.rabbitMQ;

import com.sbryan.grpcstore.rabbitMQ.model.DeliveryRabbit;
import com.sbryan.grpcstore.rabbitMQ.model.DriverRabbit;
import com.sbryan.grpcstore.service.model.DeliveryEntity;
import com.sbryan.grpcstore.service.model.DriverEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RabbitMapper {

    DeliveryEntity map(DeliveryRabbit rabbit);

    DriverEntity map(DriverRabbit rabbit);

    DriverRabbit map(DriverEntity driver);

    DeliveryRabbit map(DeliveryEntity delivery);

}
