package com.example.user_account_service.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


/**
  Service responsible for generating and parsing JWT tokens.

  The token uses the username as the subject (sub) claim and is signed using
  an HMAC secret key. Token expiration is set to a fixed duration.

  Note: In production, the secret must be stored securely (e.g., environment variables),
  not hardcoded in source code.
 */
@Service
public class JwtService {


    private static final String SECRET = "8tQv3LzPp1mKx7Nw4rYh2sJ9cD6fA0eG5uH1iB7nV3xZ9qLm";
    private static final long ONE_HOUR_MS = 60 * 60 * 1000;

    /**
      Builds the signing key used for both signing and verifying JWT tokens.

      @return HMAC secret key derived from the configured secret
     */

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
      Generates a signed JWT token for the given username.

      @param username the authenticated user's username (stored as the JWT subject)
      @return a compact JWT string
     */

    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ONE_HOUR_MS);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey())
                .compact();
    }

/**
  Extracts the username (JWT subject) from a signed token.

  @param token compact JWT string
  @return username stored in the token subject
  */
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)// parseSignedClaims verifies the signature and validates expiration
                .getPayload()
                .getSubject();
    }


}