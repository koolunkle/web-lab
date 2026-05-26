package com.example.blog.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao.api")
data class KakaoApiProperties(
    val url: String,
    val key: String
)
