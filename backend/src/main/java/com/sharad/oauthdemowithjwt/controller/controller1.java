package com.sharad.oauthdemowithjwt.controller;

import com.sharad.oauthdemowithjwt.security.AuthService;
import com.sharad.oauthdemowithjwt.security.RefreshTokenService;
import com.sharad.oauthdemowithjwt.user.RegisterUserRequest;
import com.sharad.oauthdemowithjwt.user.User;
import com.sharad.oauthdemowithjwt.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class controller1 {

    private final UserService userService;
    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    public controller1(UserService userService, AuthService authService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/home")
    public String getAllEmployees() {
        return "Welcome to Home Page!!";
    }

    @GetMapping("/secured")
    public String getSecuredPage() {
        return "Secured Page!!";
    }

    @PostMapping("/signup")
    public User registerUser(@RequestBody RegisterUserRequest user) {
        return userService.createUser(user);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            authService.renewAccessToken(request,response);
            return ResponseEntity.ok("token refreshed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RegisterUserRequest user, HttpServletResponse response) {
        try {
            authService.login(user, response);
            return ResponseEntity.ok(Map.of("username", user.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        try {
            authService.logout(response);
            return ResponseEntity.ok("logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request, HttpServletResponse response) {
        try{
            String username = authService.isAuthenticated(request, response);
            return ResponseEntity.ok(Map.of("username", username));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
