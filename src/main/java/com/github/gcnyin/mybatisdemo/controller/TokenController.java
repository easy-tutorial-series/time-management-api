package com.github.gcnyin.mybatisdemo.controller;

import com.github.gcnyin.mybatisdemo.model.Token;
import com.github.gcnyin.mybatisdemo.request.CreateTokenRequest;
import com.github.gcnyin.mybatisdemo.service.TokenService;
import org.springframework.http.HttpStatus;
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
  public Token createToken(@RequestBody CreateTokenRequest request) {
    return tokenService.createToken(request.getUsername(), request.getPassword());
  }

  @DeleteMapping("/{id}")
  public void deleteToken(@PathVariable("id") String tokenId) {
    tokenService.deleteToken(tokenId);
  }
}
