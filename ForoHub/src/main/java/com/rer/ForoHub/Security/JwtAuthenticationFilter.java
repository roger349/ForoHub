package com.rer.ForoHub.Security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                if (jwtUtil.validarToken(token)) {
                    String username = jwtUtil.extraerUsername(token);
                    List<SimpleGrantedAuthority> authorities = jwtUtil.extraerRoles(token).stream()
                            .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    LOGGER.info("Autenticación exitosa para el usuario {}", username);
                }
                else
                {
                    LOGGER.warn("Token no válido");
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token no válido");
                    return;
                }
            }
            catch (JwtException e) {
                LOGGER.error("Error al validar el token", e);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token no válido");
                return;
            }
            catch (IllegalArgumentException e) {
                LOGGER.error("Error al procesar el token", e);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token no válido");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}






