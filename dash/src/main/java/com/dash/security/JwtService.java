package com.dash.security;

import com.dash.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;
    @Value("${security.jwt.signature-key}")
    private String signatureKey;

    public String generateToken(User user) {
    long expirationMillis = Long.parseLong(expiration);
    LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(expirationMillis / (1000 * 60));

    Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());

    return Jwts.builder()
            .setSubject(user.getEmail())
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, signatureKey)
            .compact();
}


    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime expirationDateTime = LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault());
            return !LocalDateTime.now().isAfter(expirationDateTime);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserEmail(String token) throws ExpiredJwtException {
        return getClaims(token).getSubject();
    }
}
