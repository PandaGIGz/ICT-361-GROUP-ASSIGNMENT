package com.mu.studentregistrationsystem.network.models;

public class LecturerRegisterRequest {
    private String fullName;
    private String employeeNo;
    private String email;
    private String department;
    private String password;

    public LecturerRegisterRequest(String fullName, String employeeNo, String email, String department, String password) {
        this.fullName = fullName;
        this.employeeNo = employeeNo;
        this.email = email;
        this.department = department;
        this.password = password;
    }
}
