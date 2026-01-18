package com.example.user_account_service.service;

import com.example.user_account_service.entity.User;
import com.example.user_account_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService( UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    public User CreateUser(User user){
        return  userRepository.save(user);
    }

    public User getUser(Long id){
        return userRepository.findById(Math.toIntExact(id)).orElseThrow(()-> new RuntimeException("user not found")) ;
    }
    public User updateUser(Long id, User updatedUser){
        User user = getUser(id);
        user.setFirstName(updatedUser.getFirstName());
        user.setSurName(updatedUser.getSurName());
        user.setPassword(updatedUser.getPassword());
        user.setUserName(updatedUser.getUserName());
        return userRepository.save(user);
    }
}
