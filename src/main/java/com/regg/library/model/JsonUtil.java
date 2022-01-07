package com.regg.library.model;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    public static String toJson(Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
