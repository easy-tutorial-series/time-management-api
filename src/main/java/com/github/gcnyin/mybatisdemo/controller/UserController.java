package com.github.gcnyin.mybatisdemo.controller;

import com.github.gcnyin.mybatisdemo.dao.UserMapper;
import com.github.gcnyin.mybatisdemo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UserMapper userMapper;

  public UserController(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @GetMapping
  public List<User> all() {
    return userMapper.findAll();
  }

  @GetMapping("/{id}")
  public User findById(@PathVariable("id") String id) {
    return userMapper.findById(id);
  }

  @PostMapping
  public User createUser(String name) {
    return userMapper.create(name);
  }
}
