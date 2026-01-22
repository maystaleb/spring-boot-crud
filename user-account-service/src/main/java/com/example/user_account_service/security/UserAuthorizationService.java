package com.example.user_account_service.security;

import com.example.user_account_service.entity.User;
import com.example.user_account_service.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userAuth")
public class UserAuthorizationService {

    private final UserRepository userRepository;

    public UserAuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean canUpdatePassword(Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String loggedInUsername = authentication.getName(); // from JWT
        User targetUser = userRepository.findById(id)
                .orElse(null);

        return targetUser != null && loggedInUsername.equals(targetUser.getUsername());
    }
}
