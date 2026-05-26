package com.example.blog.core.response

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.validation.BindingResult

/**
 * Standardized API error response format.
 * Null fields are excluded from the JSON response to keep it clean.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val code: String,
    val message: String,
    val fieldErrors: List<FieldError>? = null
) {
    /**
     * Represents a specific validation error for a single field.
     */
    data class FieldError(
        val field: String,
        val value: String,
        val reason: String
    ) {
        companion object {
            fun of(bindingResult: BindingResult): List<FieldError> {
                return bindingResult.fieldErrors.map { error ->
                    FieldError(
                        field = error.field,
                        value = error.rejectedValue?.toString() ?: "",
                        reason = error.defaultMessage ?: "Invalid value"
                    )
                }
            }
        }
    }
}

