package com.example.ecomm.exception;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class RestApiErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(RestApiErrorHandler.class);

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(HttpServletRequest request, Exception ex, Locale locale) {
        log.error("Unhandled exception", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.GENERIC_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Error> handleHttpMediaTypeNotSupportedException(
            HttpServletRequest request,
            HttpMediaTypeNotSupportedException ex,
            Locale locale) {
        log.error("Unsupported media type requested", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                request.getRequestURI(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<Error> handleHttpMessageNotWritableException(
            HttpServletRequest request,
            HttpMessageNotWritableException ex,
            Locale locale) {
        log.error("Failed to write response body", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.HTTP_MESSAGE_NOT_WRITABLE,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Error> handleHttpMediaTypeNotAcceptableException(
            HttpServletRequest request,
            HttpMediaTypeNotAcceptableException ex,
            Locale locale) {
        log.error("None of the acceptable media types are supported", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE,
                HttpStatus.NOT_ACCEPTABLE.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleHttpMessageNotReadableException(
            HttpServletRequest request,
            HttpMessageNotReadableException ex,
            Locale locale) {
        log.error("Failed to read request body", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.HTTP_MESSAGE_NOT_READABLE,
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<Error> handleJsonParseException(
            HttpServletRequest request,
            JsonParseException ex,
            Locale locale) {
        log.error("Failed to parse JSON request body", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.JSON_PARSE_ERROR,
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFoundException(
            HttpServletRequest request,
            ResourceNotFoundException ex,
            Locale locale) {
        log.error("Resource not found", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.RESOURCE_NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Error> handleCustomerNotFoundException(
            HttpServletRequest request,
            CustomerNotFoundException ex,
            Locale locale) {
        log.error("Customer not found", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.CUSTOMER_NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Error> handleItemNotFoundException(
            HttpServletRequest request,
            ItemNotFoundException ex,
            Locale locale) {
        log.error("Item not found", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.ITEM_NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericAlreadyExistsException.class)
    public ResponseEntity<Error> handleGenericAlreadyExistsException(
            HttpServletRequest request,
            GenericAlreadyExistsException ex,
            Locale locale) {
        log.error("Resource already exists", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.GENERIC_ALREADY_EXISTS,
                HttpStatus.CONFLICT.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(
            HttpServletRequest request,
            MethodArgumentNotValidException ex,
            Locale locale) {
        log.error("Payload validation failed", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.CONSTRAINT_VIOLATION,
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<Error> handleInvalidRefreshTokenException(
            HttpServletRequest request,
            InvalidRefreshTokenException ex,
            Locale locale) {
        log.error("Invalid refresh token", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Error> handleUsernameNotFoundException(
            HttpServletRequest request,
            UsernameNotFoundException ex,
            Locale locale) {
        log.error("Authentication failed", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<Error> handleInsufficientAuthenticationException(
            HttpServletRequest request,
            InsufficientAuthenticationException ex,
            Locale locale) {
        log.error("Authentication failed", ex);

        Error error = ErrorUtils.createError(
                messageSource,
                locale,
                ErrorCode.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURL().toString(),
                request.getMethod());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
