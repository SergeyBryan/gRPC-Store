package com.sbryan.grpcstore.service.impl;

import com.sbryan.grpcstore.service.DriverService;
import com.sbryan.grpcstore.service.model.DriverEntity;
import com.sbryan.grpcstore.service.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Override
    public List<DriverEntity> getAllByActive(Boolean active) {
        return driverRepository.findAllByActive(false);
    }

    @Override
    public DriverEntity getDriverById(String driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(IllegalAccessError::new);
    }

    @Override
    public void lockDriver(String driverId, Boolean active) {
        driverRepository.updateActive(driverId, active);
    }

}
