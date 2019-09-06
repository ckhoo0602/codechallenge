package com.codechallenge.integration.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserReq {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;
    private Address address;

    @Data
    public static final class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        @Data
        public static final class Geo {
            private String lat;
            private String lng;
        }
    }

}
