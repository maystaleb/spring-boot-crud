package com.example.user_account_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
  Request DTO for updating a user's password.

  This DTO is used in secured endpoints where the authenticated user
  is allowed to update only their own password.
 */
public class UpdatePasswordRequest {


    @NotNull
    @Size(min = 6)
    private String newPassword;
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
