package com.github.gcnyin.mybatisdemo.service;

import com.github.gcnyin.mybatisdemo.mapper.TokenMapper;
import com.github.gcnyin.mybatisdemo.mapper.UserMapper;
import com.github.gcnyin.mybatisdemo.model.Token;
import com.github.gcnyin.mybatisdemo.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
  private final UserMapper userMapper;
  private final TokenMapper tokenMapper;

  public TokenService(UserMapper userMapper, TokenMapper tokenMapper) {
    this.userMapper = userMapper;
    this.tokenMapper = tokenMapper;
  }

  public Token createToken(String username) {
    Integer id = userMapper.findIdByName(username);
    String tokenId = UUID.randomUUID().toString();
    tokenMapper.createToken(tokenId, id);
    return tokenMapper.findById(tokenId);
  }

  public void touchToken(String tokenId) {
    tokenMapper.touchToken(tokenId);
  }

  public void deleteToken(String tokenId) {
    tokenMapper.deleteToken(tokenId);
  }
}
