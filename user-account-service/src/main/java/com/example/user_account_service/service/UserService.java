package com.example.user_account_service.service;

import com.example.user_account_service.entity.User;
import com.example.user_account_service.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
  Service layer for user-related business logic.

  Responsibilities:
  - Create and update user data
  - Hash passwords before persisting them
  - Enforce method-level authorization for sensitive operations (e.g., password updates)
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
      @param userRepository persistence access for users
      @param passwordEncoder encoder used to hash passwords (never store raw passwords)
     */
    public UserService( UserRepository userRepository,PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
      Updates the password of a user.

      Security:
      Access is restricted via method-level security to ensure that only the
      authenticated user can update their own password.

      @param id target user ID
      @param newPassword raw new password (will be hashed before storing)
     */

    @PreAuthorize("@userAuth.canUpdatePassword(#id, authentication)")
    public void updatePassword(Long id, String newPassword){
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("user not found"));
        user.setPassword(passwordEncoder.encode(newPassword));//Never store raw passwords - always store a one-way hash
        userRepository.save(user);
    }

    /**
      Creates a new user and hashes the provided password before saving.

      @param user user data (password is expected as raw input)
      @return created user entity
     */
    public User CreateUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash password before persisting the user
        return  userRepository.save(user);
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("user not found")) ;
    }
    /**
      Updates user profile fields. If a new password is provided, it is hashed before saving.

      @param id user ID
      @param updatedUser updated user fields
      @return updated user entity
     */
    public User updateUser(Long id, User updatedUser){
        User user = getUser(id);
        user.setFirstName(updatedUser.getFirstName());
        user.setUsername(updatedUser.getUsername());

        user.setUsername(updatedUser.getUsername());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        return userRepository.save(user);
    }
}
