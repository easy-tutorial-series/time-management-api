package com.github.gcnyin.mybatisdemo.service;

import com.github.gcnyin.mybatisdemo.exception.BadRequestException;
import com.github.gcnyin.mybatisdemo.mapper.TokenMapper;
import com.github.gcnyin.mybatisdemo.mapper.UserMapper;
import com.github.gcnyin.mybatisdemo.model.Token;
import com.github.gcnyin.mybatisdemo.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
  private final UserMapper userMapper;
  private final TokenMapper tokenMapper;
  private final PasswordEncoder passwordEncoder;

  public TokenService(UserMapper userMapper, TokenMapper tokenMapper, PasswordEncoder passwordEncoder) {
    this.userMapper = userMapper;
    this.tokenMapper = tokenMapper;
    this.passwordEncoder = passwordEncoder;
  }

  public Token createToken(String username, String password) {
    User user = userMapper.findByName(username);
    String encode = user.getPassword();
    if (!passwordEncoder.matches(password, encode)) {
      throw new BadRequestException("username or password is incorrect");
    }
    String tokenId = UUID.randomUUID().toString();
    tokenMapper.createToken(tokenId, user.getId());
    return tokenMapper.findById(tokenId);
  }

  public void touchToken(String tokenId) {
    tokenMapper.touchToken(tokenId);
  }

  public void deleteToken(String tokenId) {
    tokenMapper.deleteToken(tokenId);
  }
}
