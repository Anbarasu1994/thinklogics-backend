package com.thinklogics_backend.response;

import com.thinklogics_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class    MyResponse {
    private String token;
    private String statusText;

    private static String rejectValue;
    private String role;
    private User user;

    public static void rejectValue(String confirmPassword, String s, String passwordsDoNotMatch) {
    }
}
