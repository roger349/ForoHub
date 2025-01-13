package com.rer.ForoHub.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.function.Function;


@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Long expiration;

    public String generarToken(String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("roles", roles);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
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
    public List<String> extraerRoles(String token) {
        try {
            Claims claims = extraerAllClaims(token);
            return claims.get("roles", List.class);
        } catch (ExpiredJwtException e) {
            return Collections.emptyList();
        }
    }
   /* public List<String> extraerPermisos(String token) {
        try {
            Claims claims = extraerAllClaims(token);
            return claims.get("permisos", List.class);
        } catch (ExpiredJwtException e) {
            return Collections.emptyList();
        }
    } */
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
    public Date extraerExpiracion(String token) {
        try {
            return extraerClaim(token, Claims::getExpiration);
        } catch (ExpiredJwtException e) {
            return null;
        }
    }
    public Boolean validarToken(String token) {
        return (!isTokenExpirado(token));
    }
}

/*@Component
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
}*/




