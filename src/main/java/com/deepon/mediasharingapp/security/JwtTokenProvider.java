package com.deepon.mediasharingapp.security;

import com.deepon.mediasharingapp.config.SecurityContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtTokenProvider {
      public JwtTokenClaims getClaimsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SecurityContext.JWT_KEY.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = String.valueOf(claims.get("username"));
        JwtTokenClaims jwtClaims = new JwtTokenClaims();
        jwtClaims.setUsername(username);

        return jwtClaims;
    }
}
