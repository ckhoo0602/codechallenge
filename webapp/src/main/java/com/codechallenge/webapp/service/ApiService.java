package com.codechallenge.webapp.service;

import com.codechallenge.integration.request.UpdateUserReq;
import com.codechallenge.integration.response.GetUserResp;
import com.codechallenge.integration.response.ListUserResp;
import com.codechallenge.integration.response.SearchUserResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    @Value("${api.url}")
    private String API_URL;

    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ListUserResp listUsers() {
        return restTemplate.getForObject(API_URL + "/api/users", ListUserResp.class);
    }

    public SearchUserResp searchUserByName(String name) {
        return restTemplate.getForObject(API_URL + "/api/users/search?name=" + name, SearchUserResp.class);
    }

    public GetUserResp getUserById(Long id) {
        return restTemplate.getForObject(API_URL + String.format("/api/users/%d", id), GetUserResp.class);
    }

    public void updateUser(Long id, UpdateUserReq req) {
        restTemplate.put(API_URL + String.format("/api/users/%d", id), req);
    }

    public void deleteUser(Long id) {
        restTemplate.delete(API_URL + String.format("/api/users/%d", id));
    }
}
