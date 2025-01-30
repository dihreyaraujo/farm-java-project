package com.betrybe.agrix.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type Token service.
 */
@Service
public class TokenService {
  /**
   * Generate token string.
   *
   * @param username the username
   * @return the string
   */
  public String generateToken(String username) {
    return JWT.create()
        .withSubject(username)
        .withExpiresAt(generateExpiration())
        .sign(Algorithm.HMAC256("minha-chave-secreta"));
  }

  private Instant generateExpiration() {
    return Instant.now()
        .plus(2, ChronoUnit.HOURS);
  }

  /**
   * Validate token string.
   *
   * @param token the token
   * @return the string
   */
  public String validateToken(String token) {
    return JWT.require(Algorithm.HMAC256("minha-chave-secreta"))
        .build()
        .verify(token)
        .getSubject();
  }
}
