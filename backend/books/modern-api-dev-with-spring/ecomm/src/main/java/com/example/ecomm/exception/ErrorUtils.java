package com.example.ecomm.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;

public class ErrorUtils {

    private ErrorUtils() {
    }

    public static Error createError(
            final MessageSource messageSource,
            final Locale locale,
            final ErrorCode errorCode,
            final Integer httpStatusCode,
            final String url,
            final String reqMethod) {
        String message = messageSource.getMessage(errorCode.getErrMsgKey(), null, locale);
        return new Error(errorCode.getErrCode(), message, httpStatusCode, url, reqMethod);
    }
}
