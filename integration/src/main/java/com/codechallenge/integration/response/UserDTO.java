package com.codechallenge.integration.response;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    private UserAddressDTO address;
    private UserCompanyDTO company;

    @Data
    public static final class UserAddressDTO {
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

    @Data
    public static final class UserCompanyDTO {
        private String name;
        private String catchPhrase;
        private String bs;
    }

}
