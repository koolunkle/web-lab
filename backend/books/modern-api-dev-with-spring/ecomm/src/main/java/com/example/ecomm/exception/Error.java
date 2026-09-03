package com.example.ecomm.exception;

import org.apache.logging.log4j.util.Strings;

public record Error(
        String errorCode,
        String message,
        Integer status,
        String url,
        String reqMethod) {

    private static final String NOT_AVAILABLE = "Not available";

    public Error {
        url = Strings.isNotBlank(url) ? url : NOT_AVAILABLE;
        reqMethod = Strings.isNotBlank(reqMethod) ? reqMethod : NOT_AVAILABLE;
    }
}
