package com.example.blog.core.exception

import org.springframework.http.HttpStatus

/**
 * Defines standardized error codes, HTTP status, and default messages across the application.
 */
enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {
    /**
     * Client Errors (4xx)
     */
    INVALID_INPUT_VALUE(
        HttpStatus.BAD_REQUEST,
        "C001",
        "Invalid input value provided."
    ),

    METHOD_NOT_ALLOWED(
        HttpStatus.METHOD_NOT_ALLOWED,
        "C002",
        "HTTP method not supported."
    ),

    /**
     * Server & Infrastructure Errors (5xx)
     */
    EXTERNAL_API_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "S001",
        "An error occurred while communicating with an external API."
    ),

    INTERNAL_SERVER_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "S999",
        "An unexpected internal server error occurred."
    )
}

