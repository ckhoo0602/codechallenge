package com.codechallenge.webapp.config;

import com.codechallenge.webapp.interceptor.CustomRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class Config {

    @Bean
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }

    @Bean
    @DependsOn(value = {"customRestTemplateCustomizer"})
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder(customRestTemplateCustomizer())
                .setReadTimeout(Duration.ofSeconds(2))
                .setConnectTimeout(Duration.ofSeconds(2))
                .build();
    }
}
