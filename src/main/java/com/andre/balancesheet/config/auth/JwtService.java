package com.andre.balancesheet.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails){

        final int instantHour = 0;
        final int expirationCredential = 24;

        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(dateLocalTime(instantHour))
                .setExpiration(dateLocalTime(expirationCredential))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Date dateLocalTime(Integer hours) {
        if (hours == null) {
            return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        } else {
            return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).plusHours(hours).toInstant());
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode("${SECRET_KEY}");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
