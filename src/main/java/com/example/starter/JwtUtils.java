package com.example.starter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtUtils {
  private final String secretString = "a8jh0Vf5C0lPAuTJO2vQYBJGT1ScVdeLI12C2gXDHo8=";
  private final Key key = Keys.hmacShaKeyFor(secretString.getBytes());

  public String generate(String username) {
    var issuedAt = new Date();
    var expiration = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
    return Jwts.builder()
      .signWith(key)
      .setIssuer("me")
      .setSubject(username)
      .setAudience("you")
      .setIssuedAt(issuedAt)
      .setExpiration(expiration)
      .compact();
  }

  public Jws<Claims> parse(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
  }
}
