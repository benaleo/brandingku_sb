package com.brandingku.web.model;

import lombok.Data;

public class AuthModel {

    @Data
    public static class loginRequest {
        private String email;
        private String password;
    }
}
