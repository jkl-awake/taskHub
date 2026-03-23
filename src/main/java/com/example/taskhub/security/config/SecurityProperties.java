package com.example.taskhub.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityProperties {

    private String secret;
    private String issuer;
    private Long accessTokenExpireSeconds;
}
