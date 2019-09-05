package com.codechallenge.webapp.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body,
            ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        logRequestResponse(request, body, response);
        return response;
    }

    private void logRequestResponse(HttpRequest request, byte[] body, ClientHttpResponse response) throws IOException {
        String respString = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
        log.info("uri {}, req {}, status {}, resp {}",
                request.getURI().toString(),
                new String(body, "UTF-8"),
                response.getStatusCode(),
                respString);
    }
}
