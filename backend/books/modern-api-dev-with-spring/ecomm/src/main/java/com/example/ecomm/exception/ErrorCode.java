package com.example.ecomm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

        GENERIC_ERROR(
                        "ECOMM-0001",
                        "error.generic"),
        HTTP_MEDIATYPE_NOT_SUPPORTED(
                        "ECOMM-0002",
                        "error.http-media-type-not-supported"),
        HTTP_MESSAGE_NOT_WRITABLE(
                        "ECOMM-0003",
                        "error.http-message-not-writable"),
        HTTP_MEDIA_TYPE_NOT_ACCEPTABLE(
                        "ECOMM-0004",
                        "error.http-media-type-not-acceptable"),
        JSON_PARSE_ERROR(
                        "ECOMM-0005",
                        "error.json-parse"),
        HTTP_MESSAGE_NOT_READABLE(
                        "ECOMM-0006",
                        "error.http-message-not-readable"),
        HTTP_REQUEST_METHOD_NOT_SUPPORTED(
                        "ECOMM-0007",
                        "error.http-request-method-not-supported"),
        CONSTRAINT_VIOLATION(
                        "ECOMM-0008",
                        "error.constraint-violation"),
        ILLEGAL_ARGUMENT_EXCEPTION(
                        "ECOMM-0009",
                        "error.illegal-argument-exception"),
        RESOURCE_NOT_FOUND(
                        "ECOMM-0010",
                        "error.resource-not-found"),
        CUSTOMER_NOT_FOUND(
                        "ECOMM-0011",
                        "error.customer-not-found"),
        ITEM_NOT_FOUND(
                        "ECOMM-0012",
                        "error.item-not-found"),
        GENERIC_ALREADY_EXISTS(
                        "ECOMM-0013",
                        "error.generic-already-exists"),
        ACCESS_DENIED(
                        "ECOMM-0014",
                        "error.access-denied"),
        UNAUTHORIZED(
                        "ECOMM-0015",
                        "error.unauthorized");

        private String errCode;
        private String errMsgKey;
}
