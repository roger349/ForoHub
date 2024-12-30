package com.rer.ForoHub.security;

import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtConfiguracion jwtConfig;

    public JwtAuthenticationFilter(JwtConfiguracion jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = obtenerToken(request);

        if (token != null && !token.isEmpty()) {
            try {
                Jws<Claims> claims = Jwts.parser()
                        .setSigningKey(jwtConfig.getClaveSecreta())
                        .parseClaimsJws(token);

                String username = claims.getBody().getSubject();
                List<SimpleGrantedAuthority> authorities = obtenerRoles(claims);

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException | IllegalArgumentException e) {
                // Manejo de errores, token no válido
            }
        }

        filterChain.doFilter(request, response);
    }

    private String obtenerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private List<SimpleGrantedAuthority> obtenerRoles(Jws<Claims> claims) {
        List<String> roles = claims.getBody().get("roles", List.class);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

