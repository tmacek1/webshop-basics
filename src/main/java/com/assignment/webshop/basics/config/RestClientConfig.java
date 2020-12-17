package com.assignment.webshop.basics.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "restclient")
@Getter
@Setter
public class RestClientConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(getConnectTimeout()))
                .setReadTimeout(Duration.ofSeconds(getReadTimeout()))
                .build();
    }

    private long connectTimeout;
    private long readTimeout;
    private String baseUri;

}
