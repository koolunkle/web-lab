package com.example.blog.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    private val kakaoApiProperties: KakaoApiProperties
) {

    @Bean
    fun kakaoWebClient(): WebClient = WebClient.builder()
        .baseUrl(kakaoApiProperties.url)
        .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK ${kakaoApiProperties.key}")
        .build()
}
