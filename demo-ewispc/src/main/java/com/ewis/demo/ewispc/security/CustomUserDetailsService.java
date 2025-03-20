package com.ewis.demo.ewispc.security;

import com.ewis.demo.ewispc.model.User;
import com.ewis.demo.ewispc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
    /*
        What This Does
            Implements UserDetailsService, which is used by Spring Security.
            Uses UserRepository to fetch user details from the database.
            If the user exists, it converts it into a UserDetails object (CustomUserDetails).
            If the user does not exist, it throws an error.
     */
}
