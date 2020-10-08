package com.github.gcnyin.mybatisdemo.controller;

import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/heartbeat")
public class HeartbeatController {
  @GetMapping
  public String heartbeat() {
    return "live";
  }
}
