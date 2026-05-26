package com.example.blog.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class BlogDto(

    @field:NotBlank(message = "query parameter required")
    val query: String,

    @field:Pattern(regexp = "accuracy|recency", message = "sort parameter one of accuracy and recency")
    val sort: String = "accuracy",

    @field:Min(value = 1, message = "page is less than min")
    @field:Max(value = 15, message = "page is more than max")
    val page: Int = 1,

    @field:Min(value = 1, message = "size must be at least 1")
    @field:Max(value = 30, message = "size cannot exceed 30")
    val size: Int = 15
)
