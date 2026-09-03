package com.example.ecomm.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.security.jwt")
public class JwtProperties {

    private String keystoreLocation;
    private String keystorePassword;
    private String keyAlias;
    private String privateKeyPassphrase;
}
