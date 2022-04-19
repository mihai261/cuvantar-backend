package com.pad.cuvantar.models;

public class RestExceptionModel {
    String error;

    public RestExceptionModel(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
