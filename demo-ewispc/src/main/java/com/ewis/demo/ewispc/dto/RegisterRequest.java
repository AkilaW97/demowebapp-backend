package com.ewis.demo.ewispc.dto;

import com.ewis.demo.ewispc.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private Role role; // ADMIN or USER
}
