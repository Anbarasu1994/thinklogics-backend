package com.thinklogics_backend.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Register {
    private int userId;
    private String firstName;
    private String phoneNumber;
    private String lastName;
    private Date dob;

    private String email;
    private String password;
    private String confirmPassword;




}
