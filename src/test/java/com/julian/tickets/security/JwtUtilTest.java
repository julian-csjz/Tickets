package com.julian.tickets.security;

import com.julian.tickets.config.security.JwtUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void generateAndValidateToken() {
        String username = "test";
        String token = jwtUtil.create(username);

        assertNotNull(token);
        assertTrue(jwtUtil.isValid(token));
        assertEquals(username, jwtUtil.getUsername(token));
    }
}
