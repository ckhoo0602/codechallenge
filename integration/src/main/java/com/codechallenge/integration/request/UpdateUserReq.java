package com.codechallenge.integration.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserReq {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    private String email;

}
