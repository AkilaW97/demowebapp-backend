package com.ewis.demo.ewispc.controller;

import com.ewis.demo.ewispc.dto.LoginRequest;
import com.ewis.demo.ewispc.dto.RegisterRequest;
import com.ewis.demo.ewispc.model.User;
import com.ewis.demo.ewispc.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Updated Login Endpoint - Stores JWT in HttpOnly Cookie
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String token = authService.authenticate(request.getUsername(), request.getPassword());

        // Create an HttpOnly Cookie
        ResponseCookie jwtCookie = ResponseCookie.from("token", token)
                .httpOnly(true)   // Prevent JavaScript access (XSS protection)
                .secure(false)    // Set to true in production (requires HTTPS)
                .path("/")        // Available to all routes
                .maxAge(Duration.ofHours(1)) // 1 hour expiration
                .sameSite("Lax") // Required for frontend (CORS)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return ResponseEntity.ok("Login successful");
    }

    // Registration endpoint (No changes)
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        User newUser = authService.registerUser(request);
        return ResponseEntity.ok("User registered: " + newUser.getUsername());
    }

    // Logout - Clears Cookie
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletResponse response) {
//        ResponseCookie clearCookie = ResponseCookie.from("token", "")
//                .httpOnly(true)
//                .secure(false)
//                .path("/")
//                .maxAge(0)  // Expire immediately
//                .sameSite("None")
//                .build();
//
//        response.addHeader(HttpHeaders.SET_COOKIE, clearCookie.toString());
//        return ResponseEntity.ok("Logged out successfully");
//    }
}