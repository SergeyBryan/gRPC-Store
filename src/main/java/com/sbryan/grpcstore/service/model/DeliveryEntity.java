package com.sbryan.grpcstore.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "delivery")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeliveryEntity {

    @Id
    @Column(name = "delivery_id", nullable = false)
    @Setter
    private String deliveryId;
    @Column(name = "store_name")
    private String storeName;
    @Column(name = "delivery_type")
    private String deliveryType;
    @Column(name = "delivery_status")
    @Setter
    private String deliveryStatus;
    @Column(name = "city")
    private String city;
    @Column(name = "delivery_stock")
    private Integer deliveryStock;
    @Column(name = "delivery_sum")
    private Integer deliverySum;
    @Column(name = "delivery_weight")
    private Float deliveryWeight;
    @Column(name = "driver_id")
    @Setter
    private String driverId;

}
