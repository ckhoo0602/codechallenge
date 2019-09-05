package com.codechallenge.webapp.interceptor;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class CustomRestTemplateCustomizer implements RestTemplateCustomizer {

    @Override
    public void customize(RestTemplate restTemplate) {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(constructRequestFactory());
        restTemplate.setRequestFactory(factory);
        restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
    }

    private SimpleClientHttpRequestFactory constructRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        return requestFactory;
    }
}
