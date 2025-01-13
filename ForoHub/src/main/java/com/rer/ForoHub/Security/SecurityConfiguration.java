package com.rer.ForoHub.Security;

import com.rer.ForoHub.Services.UserDetailsServiceUsuario;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfiguration {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    UserDetailsServiceUsuario userDetailsServiceUsuario;
    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.requireCsrfProtectionMatcher(request -> false))
                .sessionManagement(se -> se.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login", "/registrarUsuario").permitAll()
                        .requestMatchers("/swagger-ui", "/v3/api-docs","/swagger-ui.html").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException)
                        -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                .accessDeniedHandler((request, response, accessDeniedException)
                        -> response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
       return http.getSharedObject(AuthenticationManagerBuilder.class)
               .userDetailsService(userDetailsServiceUsuario)
               .passwordEncoder(passwordEncoder).and()
               .build();
   }
}







