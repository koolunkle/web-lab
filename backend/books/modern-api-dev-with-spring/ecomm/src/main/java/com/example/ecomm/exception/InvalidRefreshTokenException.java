package com.example.ecomm.exception;

import java.io.Serial;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidRefreshTokenException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String errMsgKey;
    private final String errorCode;

    public InvalidRefreshTokenException(final String message) {
        super(message);
        this.errMsgKey = ErrorCode.UNAUTHORIZED.getErrMsgKey();
        this.errorCode = ErrorCode.UNAUTHORIZED.getErrCode();
    }
}