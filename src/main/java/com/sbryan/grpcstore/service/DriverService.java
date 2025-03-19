package com.sbryan.grpcstore.service;

import com.sbryan.grpcstore.service.model.DriverEntity;

import java.util.List;

public interface DriverService {
    List<DriverEntity> getAllByActive(Boolean active);

    DriverEntity getDriverById(String driverId);

    void lockDriver(String driverId, Boolean active);
}
