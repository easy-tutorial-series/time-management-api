package com.github.gcnyin.mybatisdemo.service;

import com.github.gcnyin.mybatisdemo.exception.BadRequestException;
import com.github.gcnyin.mybatisdemo.mapper.AuthorityMapper;
import com.github.gcnyin.mybatisdemo.mapper.UserMapper;
import com.github.gcnyin.mybatisdemo.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UserMapper userMapper;
  private final AuthorityMapper authorityMapper;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserMapper userMapper, AuthorityMapper authorityMapper, PasswordEncoder passwordEncoder) {
    this.userMapper = userMapper;
    this.authorityMapper = authorityMapper;
    this.passwordEncoder = passwordEncoder;
  }

  public User createUser(String name, String password) {
    if (userMapper.countByName(name) >= 1) {
      throw new BadRequestException("user already exists");
    }
    String encode = passwordEncoder.encode(password);
    userMapper.create(name, encode);
    authorityMapper.createDefaultAuthority(userMapper.findIdByName(name));
    return userMapper.findByName(name);
  }

  public List<User> findAll() {
    return userMapper.findAll();
  }
}
