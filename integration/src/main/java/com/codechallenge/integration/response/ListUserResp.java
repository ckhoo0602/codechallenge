package com.codechallenge.integration.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListUserResp {
    private List<UserDTO> users = new ArrayList<>();
}
