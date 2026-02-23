package com.cefii.learning.order_management.security;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

@Component
@Profile("test")
public class TestJwtUtil extends JwtUtil {
    public TestJwtUtil() {
        super("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=", 3600000L);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return "test-token-for-" + userDetails.getUsername();
    }

    @Override
    public String extractUsername(String token) {
        if (token != null && token.startsWith("test-token-for-")) {
            return token.substring("test-token-for-".length());
        }
        return null;
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username != null && username.equals(userDetails.getUsername());
    }
}

