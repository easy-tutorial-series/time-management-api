package com.github.gcnyin.mybatisdemo.controller;

import com.github.gcnyin.mybatisdemo.model.Token;
import com.github.gcnyin.mybatisdemo.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {
  private final TokenService tokenService;

  public TokenController(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public Token createToken() {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    return tokenService.createToken(name);
  }

  @DeleteMapping("/{id}")
  public void deleteToken(@PathVariable("id") String tokenId) {
    tokenService.deleteToken(tokenId);
  }
}
