package com.ewis.demo.ewispc.security;

import com.ewis.demo.ewispc.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    // ✅ Corrected `getRole()` method to retrieve role from `User`
    public String getRole() {
        return user.getRole().name();  // ✅ Correct way to get role
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /*
        What This Does:
            Converts User object into a Spring Security UserDetails object.
            Maps roles (ADMIN or USER) to Spring Security authorities.
            Provides password and username for authentication.
            Ensures `getRole()` method retrieves role properly.
     */
}
