package com.example.user_account_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
  JWT authentication filter that runs once per request.

  Responsibilities:
  1) Extract JWT from the Authorization header (Bearer token)
  2) Parse the token and extract the username
  3) Load user details from the data source
  4) Populate the Spring SecurityContext with an authenticated principal

  After this filter completes, downstream components can rely on
  SecurityContextHolder to determine the authenticated user.
 */

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
      Intercepts incoming HTTP requests and authenticates the user based on JWT.

      @param request incoming HTTP request
      @param response outgoing HTTP response
      @param filterChain remaining filter chain
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");// Read the Authorization header (expected format: "Bearer <token>")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {//If no JWT is provided, continue without authentication (public endpoints may exist)
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);//Extract token string (remove "Bearer ")
        String username = jwtService.extractUsername(token);//Extract username (subject) from the token

        //Authenticate only if username exists and no authentication is already set for this request
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);//Load user details

            UsernamePasswordAuthenticationToken authToken =//Build an Authentication object and attach authorities to the security context
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,//credentials not needed here (JWT is the proof)
                            userDetails.getAuthorities()
                    );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//Attach request details for auditing/diagnostics

            SecurityContextHolder.getContext().setAuthentication(authToken);//Store the authenticated user in the SecurityContext for downstream access control

        }

        filterChain.doFilter(request, response);//Continue the filter chain
    }
}
