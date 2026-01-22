package com.example.user_account_service.controller;

import com.example.user_account_service.dto.UpdatePasswordRequest;
import com.example.user_account_service.entity.User;
import com.example.user_account_service.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping

    public User CreateUser(@Valid @RequestBody User user){
        return userService.CreateUser(user);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id , @Valid @RequestBody UpdatePasswordRequest request){
        userService.updatePassword(id, request.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")

    public User getUser(@PathVariable  Long id){
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
     public User updateUser(@PathVariable  Long id , @Valid @RequestBody User user){
        return userService.updateUser(id,user);

     }

}
