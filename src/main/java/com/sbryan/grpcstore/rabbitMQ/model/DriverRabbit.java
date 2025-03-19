package com.sbryan.grpcstore.rabbitMQ.model;

import lombok.Data;

@Data
public class DriverRabbit {

    private String id;
    private String name;
    private String phoneNumber;
    private Integer age;
    private Boolean active;

}
