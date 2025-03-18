package com.gossip_point.app.config;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenProvider {

    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateTokenFromEmail(String email) {
        return Jwts.builder()
                .setIssuer("Gossip-Point")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) 
                .claim("email", email)
                .signWith(key) 
                .compact();
    }

    public String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7); 
        Claims claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        return claim.get("email", String.class);
    }
}
