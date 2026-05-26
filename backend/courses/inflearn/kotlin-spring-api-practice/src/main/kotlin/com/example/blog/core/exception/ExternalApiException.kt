package com.example.blog.core.exception

/**
 * Thrown when an external API (like Kakao) returns an error response.
 */
class ExternalApiException(
    message: String? = null
) : BusinessException(ErrorCode.EXTERNAL_API_ERROR, message)

