package com.github.gcnyin.mybatisdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/heartbeat")
public class HeartbeatController {
  private final Map<String, String> response = Map.of("status", "live");

  @GetMapping
  public Map<String, String> heartbeat() {
    return response;
  }
}
