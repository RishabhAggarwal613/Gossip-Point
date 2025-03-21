package com.gossip_point.app.config;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class TokenProvider {

    private final SecretKey key;
    private final long tokenValidity;

    public TokenProvider(@Value("${jwt.secret}") String secret, 
                        @Value("${jwt.expiration}") long tokenValidityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.tokenValidity = tokenValidityInMilliseconds;
    }

    public String generateToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidity);

        return Jwts.builder()
                .setIssuer("Gossip-Point")
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("email", email)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }

        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration().before(new Date())) {
                throw new ExpiredJwtException(null, claims, "Token has expired");
            }
                    
            return claims.get("email", String.class);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token has expired");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT token");
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported JWT token");
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT claims string is empty");
        }
    }

    public boolean validateToken(String token) {
        if (token == null) {
            return false;
        }

        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}