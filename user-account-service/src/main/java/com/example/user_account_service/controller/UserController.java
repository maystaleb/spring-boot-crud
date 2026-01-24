package com.example.user_account_service.controller;

import com.example.user_account_service.dto.UpdatePasswordRequest;
import com.example.user_account_service.entity.User;
import com.example.user_account_service.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
  REST controller responsible for user management operations.
  Exposes endpoints for creating users, retrieving user details,
  updating user information, and securely updating user passwords.

 * Security note:
  Sensitive operations (such as password updates) are protected at
  the service layer using method-level security and JWT authentication.
 */


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    //Constructor-based dependency injection for UserService.
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping

    // Creates a new user account
    public User CreateUser(@Valid @RequestBody User user){
        return userService.CreateUser(user);
    }

    /**
      Updates the password of a specific user.

      This endpoint delegates authorization checks to the service layer,
      ensuring that only the authenticated user can update their own password.

      @param id the ID of the user whose password is being updated
      @param request DTO containing the new password
      @return HTTP 200 OK if the password was updated successfully
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id , @Valid @RequestBody UpdatePasswordRequest request){
        userService.updatePassword(id, request.getNewPassword());//Password update logic and authorization validation are handled in the service layer

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")

    public User getUser(@PathVariable  Long id){
        return userService.getUser(id);
    }

    //update user
    @PutMapping("/{id}")
     public User updateUser(@PathVariable  Long id , @Valid @RequestBody User user){
        return userService.updateUser(id,user);

     }

}
