package com.example.user_account_service.controller;

import com.example.user_account_service.entity.User;
import com.example.user_account_service.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping

    public User CreateUser(@RequestBody User user){
        return userService.CreateUser(user);
    }


    @GetMapping("/{id}")

    public User getUser(@PathVariable  Long id){
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
     public User updateUser(@PathVariable  Long id , @RequestBody User user){
        return userService.updateUser(id,user);

     }

}
