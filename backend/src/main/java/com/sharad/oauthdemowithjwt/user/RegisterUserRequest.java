package com.sharad.oauthdemowithjwt.user;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String username;
    private String password;
}
