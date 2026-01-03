package com.example.bankega.component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "secret_key_ega_2026_secret_secret";
    private final long ACCESS_EXP = 15 * 60 * 1000; // 15MIN
    private final long REFRESH_EXP = 7 * 24 * 60 * 60 * 1000; // 7JOUR

    public  String genretedAccessToken(String username){
        return generateToken(username,ACCESS_EXP);
    }

    public  String genretedRefreshToken(String username){
        return generateToken(username,REFRESH_EXP);
    }

    public String generateToken(String username, long expiration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration
                ))
                .signWith(
                        Keys.hmacShaKeyFor(SECRET.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
