package com.example.user_account_service.dto;

/**
  Response DTO returned after a successful authentication.

  Contains the generated JWT token and basic user identification data.
  The token must be sent in the Authorization header for subsequent requests.
 */
public class LoginResponse {
    private String token;//JWT token generated after successful authentication.
    private String username;//sername of the authenticated user



    public LoginResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }
    public LoginResponse(){}



    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }





}
