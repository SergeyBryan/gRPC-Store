package com.sbryan.grpcstore.elasticsearch.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
public class DriverEntry {

    @Field(name = "id")
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "phoneNumber")
    private String phoneNumber;
    @Field(name = "age")
    private Integer age;
    @Field(name = "active")
    private Boolean active;

}
