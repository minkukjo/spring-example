package com.example.example.infrastructure.security.property;

import com.example.example.infrastructure.config.YamlPropertySourceFactory;
import com.example.example.infrastructure.security.AuthTokenProvider;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertySourceFactory.class)
public class JwtAuthProperty {

    private String secret;

    private String redirectUri;

    @Bean
    public AuthTokenProvider jwtProvider() {
        return new AuthTokenProvider(secret);
    }
}
