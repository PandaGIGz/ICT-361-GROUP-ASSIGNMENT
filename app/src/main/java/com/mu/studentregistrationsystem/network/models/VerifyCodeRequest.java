package com.mu.studentregistrationsystem.network.models;

public class VerifyCodeRequest {
    private String email;
    private String code;

    public VerifyCodeRequest(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
