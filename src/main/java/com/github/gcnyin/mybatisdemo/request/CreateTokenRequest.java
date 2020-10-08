package com.github.gcnyin.mybatisdemo.request;

import lombok.Data;

@Data
public class CreateTokenRequest {
  private String username;
  private String password;
}
