package com.example.user_account_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
  Spring Security configuration for a stateless JWT-based API.

  Key decisions:
  CSRF is disabled because the API is stateless and does not use cookies/sessions.
  Form login and HTTP Basic are disabled (authentication is done via JWT).
  Session creation is disabled (STATELESS).
  Public endpoints are explicitly permitted (login + user registration).
  JWT filter is placed before UsernamePasswordAuthenticationFilter to populate the SecurityContext.
 */

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {//@param jwtAuthFilter filter responsible for extracting and validating JWT tokens per request

        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
      Defines the application's security filter chain and authorization rules.

      @param http HttpSecurity builder
      @return configured SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {//

        http
                // Stateless REST APIs typically disable CSRF (no server-side session, no cookies)
                .csrf(csrf -> csrf.disable())
                // Disable default authentication mechanisms; we rely on JWT instead

                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Authorization rules:
                // allow login and user registration without a token
                //protect all other endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .anyRequest().authenticated()
                )
                // Run JWT filter before the standard username/password auth filter

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
      Exposes the AuthenticationManager used by the login controller.

      @param cfg AuthenticationConfiguration provided by Spring
      @return authentication manager instance
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
}
