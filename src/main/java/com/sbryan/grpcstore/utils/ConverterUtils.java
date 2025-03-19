package com.sbryan.grpcstore.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConverterUtils {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T readJsonAsString(String message, Class<T> clazz) {
        try {
            return mapper.readValue(message, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
