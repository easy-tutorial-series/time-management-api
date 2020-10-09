package com.github.gcnyin.mybatisdemo.controller;

import com.github.gcnyin.mybatisdemo.model.User;
import com.github.gcnyin.mybatisdemo.request.CreateUserRequest;
import com.github.gcnyin.mybatisdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> all() {
    return userService.findAll();
  }

  @PostMapping
  public User createUser(@RequestBody CreateUserRequest request) {
    return userService.createUser(request.getName(), request.getPassword());
  }
}
