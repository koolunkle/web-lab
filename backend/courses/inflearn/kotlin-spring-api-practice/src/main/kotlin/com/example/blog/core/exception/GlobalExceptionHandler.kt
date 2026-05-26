package com.example.blog.core.exception

import com.example.blog.core.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Handles custom business exceptions.
     */
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {
        log.warn("BusinessException: {}", e.message)
        val errorCode = e.errorCode
        val response = ErrorResponse(
            code = errorCode.code,
            message = e.message ?: errorCode.message
        )
        return ResponseEntity(response, errorCode.status)
    }

    /**
     * Handles DTO validation failures (@Valid).
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        log.warn("MethodArgumentNotValidException: {}", e.message)
        val errorCode = ErrorCode.INVALID_INPUT_VALUE
        val response = ErrorResponse(
            code = errorCode.code,
            message = errorCode.message,
            fieldErrors = ErrorResponse.FieldError.of(e.bindingResult)
        )
        return ResponseEntity(response, errorCode.status)
    }

    /**
     * Fallback handler for all unexpected exceptions.
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("Unhandled Exception", e)
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
        val response = ErrorResponse(
            code = errorCode.code,
            message = errorCode.message
        )
        return ResponseEntity(response, errorCode.status)
    }
}

