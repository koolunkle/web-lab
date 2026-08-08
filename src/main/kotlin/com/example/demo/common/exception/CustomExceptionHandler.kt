package com.example.demo.common.exception

import com.example.demo.common.dto.BaseResponse
import com.example.demo.common.status.ResultCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {

    companion object {
        private const val NOT_EXCEPTION_MESSAGE = "Not Exception Message"
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    private fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mutableMapOf<String, String>()

        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            errors[fieldName] = errorMessage ?: NOT_EXCEPTION_MESSAGE
        }

        return ResponseEntity(
            BaseResponse(
                ResultCode.ERROR.name, errors, ResultCode.ERROR.msg
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(InvalidInputException::class)
    private fun invalidInputException(ex: InvalidInputException): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mapOf(ex.fieldName to (ex.message ?: NOT_EXCEPTION_MESSAGE))
        return ResponseEntity(
            BaseResponse(
                ResultCode.ERROR.name, errors, ResultCode.ERROR.msg
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(BadCredentialsException::class)
    private fun badCredentialsException(): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mapOf("로그인 실패" to "아이디 또는 비밀번호를 다시 확인하세요.")
        return ResponseEntity(
            BaseResponse(
                ResultCode.ERROR.name, errors, ResultCode.ERROR.msg
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    private fun defaultException(ex: Exception): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mapOf("미처리 에러" to (ex.message ?: NOT_EXCEPTION_MESSAGE))
        return ResponseEntity(
            BaseResponse(
                ResultCode.ERROR.name, errors, ResultCode.ERROR.msg
            ), HttpStatus.BAD_REQUEST
        )
    }
}