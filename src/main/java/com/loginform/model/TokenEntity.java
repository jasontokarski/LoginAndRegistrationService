package com.loginform.model;

public class TokenEntity {
    private String token;
    private StatusEntity statusEntity;

    public TokenEntity() {
    }

    public TokenEntity(String token, StatusEntity statusEntity) {
        this.token = token;
        this.statusEntity = statusEntity;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public StatusEntity getStatusEntity() {
        return this.statusEntity;
    }

    public void setStatusEntity(StatusEntity statusEntity) {
        this.statusEntity = statusEntity;
    }
}

