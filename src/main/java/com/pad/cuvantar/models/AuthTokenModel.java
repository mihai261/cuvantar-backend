package com.pad.cuvantar.models;

public class AuthTokenModel {
    String token;

    public AuthTokenModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
