package com.example.ecomm;

import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

import com.example.ecomm.config.ObjectMapperConfig;

import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class TestUtils {

    private static ObjectMapper objectMapper;

    public static boolean isTokenExpired(String jwt) {
        var encodedPayload = jwt.split("\\.")[1];
        var payload = new String(Base64.getDecoder().decode(encodedPayload));

        JsonNode parent = new ObjectMapper().readTree(payload);
        String expiration = parent.path("exp").asString();
        Instant expTime = Instant.ofEpochMilli(Long.valueOf(expiration) * 1000L);

        return Instant.now().compareTo(expTime) < 0;
    }

    public static ObjectMapper objectMapper() {
        if (Objects.isNull(objectMapper)) {
            objectMapper = new ObjectMapperConfig().objectMapper()
                    .rebuild()
                    .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                    .build();
        }

        return objectMapper;
    }
}
