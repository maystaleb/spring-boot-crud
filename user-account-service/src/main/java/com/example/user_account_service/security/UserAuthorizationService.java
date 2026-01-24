package com.example.user_account_service.security;

import com.example.user_account_service.entity.User;
import com.example.user_account_service.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
  Authorization service used for method-level security decisions.

  Determines whether the currently authenticated user is allowed
  to perform sensitive operations on user resources.

  This service is typically invoked from @PreAuthorize expressions
  in the service layer.
 */
@Component("userAuth")
public class UserAuthorizationService {

    private final UserRepository userRepository;
    // @param userRepository repository used to fetch user data for authorization checks
    public UserAuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**
      Checks whether the authenticated user is allowed to update the password
      of the target user identified by ID.

      Authorization logic:
      - The request must be authenticated
      - The authenticated user's username (from JWT) must match
        the username of the target user in the database

      @param id ID of the user whose password is being updated
      @param authentication Spring Security authentication object
      @return true if the authenticated user is allowed to update the password; false otherwise
     */

    public boolean canUpdatePassword(Long id, Authentication authentication) {
        //Reject unauthenticated or anonymous requests
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        // Username of the currently authenticated user (extracted from JWT)
        String loggedInUsername = authentication.getName(); // from JWT
        User targetUser = userRepository.findById(id)
                .orElse(null);
        // Allow password update only if the authenticated user matches the target user
        return targetUser != null && loggedInUsername.equals(targetUser.getUsername());
    }
}
