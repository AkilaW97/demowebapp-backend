package com.ewis.demo.ewispc.controller;

import com.ewis.demo.ewispc.dto.LoginRequest;
import com.ewis.demo.ewispc.dto.RegisterRequest;
import com.ewis.demo.ewispc.model.User;
import com.ewis.demo.ewispc.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Login endpoint for JSON requests.
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginJson(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(token);
    }

    // Login endpoint for application/x-www-form-urlencoded requests.
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> loginForm(@ModelAttribute LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(token);
    }

    // Registration endpoint (only JSON is accepted)
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        User newUser = authService.registerUser(request);
        return ResponseEntity.ok("User registered: " + newUser.getUsername());
    }
}

    /*
     How This Works
        Exposes /api/auth/login endpoint.
        Accepts username and password.
        Returns a JWT token if login is successful.
     */

