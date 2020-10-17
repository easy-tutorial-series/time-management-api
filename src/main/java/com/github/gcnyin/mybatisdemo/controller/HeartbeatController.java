package com.github.gcnyin.mybatisdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
public class HeartbeatController {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping
  public String heartbeat() {
    return "live";
  }
}
