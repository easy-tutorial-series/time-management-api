package com.example.starter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtUtils {
  private final Key key;

  public JwtUtils(Key key) {
    this.key = key;
  }

  public String generate(String username) {
    Date issuedAt = new Date();
    Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
    return Jwts.builder()
      .signWith(key)
      .setSubject(username)
      .setIssuedAt(issuedAt)
      .setExpiration(expiration)
      .compact();
  }

  public Jws<Claims> parse(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
  }

  public Claims parseBody(String token) {
    return this.parse(token).getBody();
  }
}
