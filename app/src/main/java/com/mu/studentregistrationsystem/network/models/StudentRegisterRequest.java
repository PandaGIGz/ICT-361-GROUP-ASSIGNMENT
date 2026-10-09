package com.mu.studentregistrationsystem.network.models;

public class StudentRegisterRequest {
    private String name;
    private String studentNumber;
    private String email;
    private String programme;
    private String password;

    public StudentRegisterRequest(String name, String studentNumber, String email, String programme, String password) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.email = email;
        this.programme = programme;
        this.password = password;
    }
}
