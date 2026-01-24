package com.example.user_account_service.security;

import com.example.user_account_service.entity.User;
import com.example.user_account_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
  Custom implementation of {@link UserDetailsService}.

  Used by Spring Security during authentication to load user credentials
  from the application's data source (via {@link UserRepository}).

  The returned {@link UserDetails} is later used to validate credentials
  and to populate the authenticated principal in the SecurityContext.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**
      Loads a user by username for Spring Security authentication.

      @param username username provided during login
      @return {@link UserDetails} containing username, hashed password, and authorities
      @throws UsernameNotFoundException if no matching user is found
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found"));
        return org.springframework.security.core.
                userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER").build();
    }


}
