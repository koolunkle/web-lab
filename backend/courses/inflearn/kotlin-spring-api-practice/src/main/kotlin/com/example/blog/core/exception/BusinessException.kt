package com.example.blog.core.exception

/**
 * Base exception class for all custom business logic exceptions.
 * Ensures consistent handling by the GlobalExceptionHandler.
 */
open class BusinessException(
    val errorCode: ErrorCode,
    override val message: String? = errorCode.message
) : RuntimeException(message)

