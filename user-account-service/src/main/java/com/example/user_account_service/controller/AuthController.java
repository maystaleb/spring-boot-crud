package com.example.user_account_service.controller;


import com.example.user_account_service.dto.LoginRequest;
import com.example.user_account_service.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
Authentication controller responsible for user login.

 Handles credential validation and JWT token generation.
 This is the only public endpoint that does not require
 prior authentication.

 */
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
      Constructs the authentication controller with required dependencies.
      @param authenticationManager Spring Security authentication manager
      @param jwtService service responsible for JWT generation
     */
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }



    /**
      Authenticates a user and returns a JWT token upon successful login.

      Authentication is delegated to Spring Security.
      If credentials are invalid, an authentication exception is thrown
      automatically by the framework.

      @param loginRequest DTO containing username and password
     @return a map containing the generated JWT token
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        //// Authenticate user credentials using Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String token = jwtService.generateToken(loginRequest.getUsername()); //Generate JWT token after successful authentication

        return Map.of("token", token);
    }

}
