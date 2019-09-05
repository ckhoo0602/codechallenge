package com.codechallenge.api.model;

import lombok.Data;

@Data
public class UserImportModel {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    private Company company;
    private Address address;

    @Data
    public static final class Company {
        private String name;
        private String catchPhrase;
        private String bs;
    }

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
