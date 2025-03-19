package com.sbryan.grpcstore.elasticsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Data
@Document(indexName = "delivery")
@Setting(useServerConfiguration = true)
public class DeliveryEntry {

    @Id
    private String deliveryId;
    @Field(name = "storeName")
    private String storeName;
    @Field(name = "city")
    private String city;
    @Field(name = "deliveryStatus")
    private String deliveryStatus;
    @Field(name = "deliveryType")
    private String deliveryType;
    @Field(name = "deliveryStock")
    private Integer deliveryStock;
    @Field(name = "deliverySum")
    private Integer deliverySum;
    @Field(name = "deliveryWeight")
    private Float deliveryWeight;
    @Field(name = "driver", type = FieldType.Nested)
    private DriverEntry driverEntry;
}
