package com.example.ecomm.exception;

import java.io.Serial;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GenericAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String errMsgKey;
    private final String errorCode;

    public GenericAlreadyExistsException(final String message) {
        super(message);
        this.errMsgKey = ErrorCode.GENERIC_ALREADY_EXISTS.getErrMsgKey();
        this.errorCode = ErrorCode.GENERIC_ALREADY_EXISTS.getErrCode();
    }
}
