package com.pocketbazaar.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {

    public static Logger logger = LoggerFactory.getLogger(JwtUtil.class);



  private final SecretKey secretKey;
  @Value("${jwt.expiration}")
    private long expirationTime;

    // Constructor to initialize the secret key
    public JwtUtil(@Value("${jwt.secret}") String jwtSecret) {
        byte[] apiKeySecretBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes); 
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)  // Use the secure key here
                .compact();
    }



    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract a specific claim (like subject) from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)  
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Invalid or expired token", e);
        }
    }

    // Validate token
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Validate token (username matches and not expired)
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token); 
    }
   
}
