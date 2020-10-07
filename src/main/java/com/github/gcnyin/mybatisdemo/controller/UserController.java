package com.github.gcnyin.mybatisdemo.controller;

import com.github.gcnyin.mybatisdemo.mapper.UserMapper;
import com.github.gcnyin.mybatisdemo.model.User;
import com.github.gcnyin.mybatisdemo.request.CreateUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping
  public List<User> all() {
    return userMapper.findAll();
  }

  @GetMapping("/{id}")
  public User findById(@PathVariable("id") Integer id) {
    return userMapper.findById(id);
  }

  @PostMapping
  public void createUser(@RequestBody CreateUser request) {
    String encode = passwordEncoder.encode(request.getPassword());
    int i = userMapper.create(request.getName(), encode);
    log.info("insert {} rows", i);
  }
}
