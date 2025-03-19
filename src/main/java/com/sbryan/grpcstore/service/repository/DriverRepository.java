package com.sbryan.grpcstore.service.repository;

import com.sbryan.grpcstore.service.model.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverRepository extends JpaRepository<DriverEntity, String> {

    List<DriverEntity> findAllByActive(Boolean active);

    @Query(value = "UPDATE driver set active = :active where id = :driverId", nativeQuery = true)
    @Modifying(flushAutomatically = true)
    void updateActive(@Param("driverId") String driverId, @Param("active") Boolean active);
}
