package com.sbryan.grpcstore.activeMQ.model;

import lombok.Data;

@Data
public class DriverActive {

    private String id;
    private String name;
    private String phoneNumber;
    private Integer age;
    private Boolean active;

}
