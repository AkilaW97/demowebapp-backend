package com.ewis.demo.ewispc.security;

import com.ewis.demo.ewispc.model.User;
import io.jsonwebtoken.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // Load the secret key from application.properties
    private String secretKey;

    public String generateToken(UserDetails userDetails) {
        if (!(userDetails instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("Invalid user details type");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails; // ✅ Correct Cast

        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .claim("role", customUserDetails.getRole())  // ✅ Correct way to get the role
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours expiry
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, @NotNull Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
    /*
        What This Does
            generateToken() → Creates a JWT token (valid for 1 hour).
            validateToken() → Ensures the token is valid and not expired.
            extractUsername() → Retrieves the username from the token.
     */

    /*
        Key Fixes:
            ✔ Loads secret key dynamically → Uses @Value("${jwt.secret}")
            ✔ Removes hardcoded secret key → More secure and easier to update
            ✔ Now matches your OpenSSL-generated key from application.properties
     */

