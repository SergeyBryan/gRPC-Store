package com.sbryan.grpcstore.service.repository;

import com.sbryan.grpcstore.service.model.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, String> {

    @Query(value = "SELECT max(cast(delivery_id as int)) from delivery", nativeQuery = true)
    Integer findTopByDeliveryId();

}
