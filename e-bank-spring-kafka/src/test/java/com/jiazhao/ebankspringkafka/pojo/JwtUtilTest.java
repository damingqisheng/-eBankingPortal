package com.jiazhao.ebankspringkafka.pojo;

import com.jiazhao.ebankspringkafka.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"jwt.secret=test-secret"})
public class JwtUtilTest {

    @Value("${jwt.secret}")
    private String secret;

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    public void generateToken_shouldReturnNonNull() {
        String token = jwtUtil.generateToken("test");
        assertNotNull(token);
    }

    @Test
    public void extractUsername_shouldReturnCorrectUsername() {
        String token = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject("test-user")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        String username = jwtUtil.extractUsername(token);
        assertEquals("test-user", username);
    }

    @Test
    public void extractExpiration_shouldReturnCorrectExpiration() {
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);

        String token = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject("test")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        Date extractedExpiration = jwtUtil.extractExpiration(token);
        assertEquals(expiration, extractedExpiration);
    }

    @Test
    public void validateToken_shouldReturnTrueForValidToken() {
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);

        String token = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject("test")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    public void validateToken_shouldReturnFalseForExpiredToken() throws InterruptedException {
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 1000);

        String token = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject("test")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        Thread.sleep(1000);

        assertFalse(jwtUtil.validateToken(token));
    }

    @Test
    public void validateToken_shouldReturnFalseForInvalidToken() {
        String token = "invalid-token";

        assertFalse(jwtUtil.validateToken(token));
    }

}
