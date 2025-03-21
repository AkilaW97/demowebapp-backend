package com.ewis.demo.ewispc.service;

import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class LogoutService {
    private final Set<String> blacklistedTokens = new HashSet<>();

    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
