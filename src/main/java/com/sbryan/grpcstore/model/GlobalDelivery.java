package com.sbryan.grpcstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalDelivery {

    @JsonProperty("delivery")
    private Delivery delivery;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Delivery {
        @JsonProperty("deliveryId")
        private String deliveryId;
        @JsonProperty("storeName")
        private String storeName;
        @JsonProperty("city")
        private String city;
        @JsonProperty("deliveryStatus")
        private String deliveryStatus;
        @JsonProperty("deliveryType")
        private String deliveryType;
    }
}
