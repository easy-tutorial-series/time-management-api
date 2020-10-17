package com.github.gcnyin.mybatisdemo.controller;

import com.github.gcnyin.mybatisdemo.model.User;
import com.github.gcnyin.mybatisdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
