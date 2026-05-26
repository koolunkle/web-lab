package com.example.aircraft.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Value($$"${external.url:http://localhost:7634}")
    lateinit var externalUrl: String

    @Bean
    fun webClient(): WebClient {
        return WebClient.create(externalUrl)
    }
}
