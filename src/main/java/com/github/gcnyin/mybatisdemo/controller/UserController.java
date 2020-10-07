package com.github.gcnyin.mybatisdemo.controller;

import com.github.gcnyin.mybatisdemo.mapper.UserMapper;
import com.github.gcnyin.mybatisdemo.model.User;
import com.github.gcnyin.mybatisdemo.request.CreateUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public UserController(UserMapper userMapper, PasswordEncoder passwordEncoder) {
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping
  public User createUser(@RequestBody CreateUser request) {
    String encode = passwordEncoder.encode(request.getPassword());
    String name = request.getName();
    userMapper.create(name, encode);
    return userMapper.findByName(name);
  }
}
