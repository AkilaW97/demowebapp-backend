package com.ewis.demo.ewispc.service;

import com.ewis.demo.ewispc.dto.RegisterRequest;
import com.ewis.demo.ewispc.model.Role;
import com.ewis.demo.ewispc.model.User;
import com.ewis.demo.ewispc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final com.ewis.demo.ewispc.security.JwtUtil jwtUtil;

    public String authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // Fetch the User entity
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        // Convert to UserDetails using CustomUserDetails
        UserDetails userDetails = new com.ewis.demo.ewispc.security.CustomUserDetails(user);
        return jwtUtil.generateToken(userDetails);
    }


    // ✅ New Register Method
    public User registerUser(RegisterRequest request) {
        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // ✅ Encrypt password
                .role(request.getRole() != null ? request.getRole() : Role.USER) // Default role: USER
                .build();

        return userRepository.save(newUser);
    }
}

