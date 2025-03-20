package com.ewis.demo.ewispc.repository;

import com.ewis.demo.ewispc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    /*
        Why?
            JpaRepository<User, Long> gives built-in CRUD methods.
f           indByUsername(String username) helps find users by username (useful for login).
     */
}

