package com.rer.ForoHub.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 2000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String extraerUsername(String token) {
        try {
            return extraerClaim(token, Claims::getSubject);
        } catch (ExpiredJwtException e) {
            return null;
        }
    }
    public Date extraerExpiracion(String token) {
        try {
            return extraerClaim(token, Claims::getExpiration);
        } catch (ExpiredJwtException e) {
            return null;
        }
    }
    public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraerAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extraerAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpirado(String token) {
        return extraerExpiracion(token).before(new Date());
    }
    public Boolean validarToken(String token) {
        return (!isTokenExpirado(token));
    }
}




