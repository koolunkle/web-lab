package com.eazybytes.eazystore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@Configuration
// @PropertySource("file:stripe.properties")
@PropertySource(value = "classpath:stripe.properties", ignoreResourceNotFound = true)
public class StripeConfig {

    @Value("${stripe.apiKey}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }
}
