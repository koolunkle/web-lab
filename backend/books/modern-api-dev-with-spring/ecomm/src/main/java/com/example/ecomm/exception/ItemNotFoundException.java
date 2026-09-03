package com.example.ecomm.exception;

import java.io.Serial;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String errMsgKey;
    private final String errorCode;

    public ItemNotFoundException(final String message) {
        super(message);
        this.errMsgKey = ErrorCode.ITEM_NOT_FOUND.getErrMsgKey();
        this.errorCode = ErrorCode.ITEM_NOT_FOUND.getErrCode();
    }
}
