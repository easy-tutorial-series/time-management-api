package com.github.gcnyin.mybatisdemo.request;

import lombok.Data;

@Data
public class CreateUserRequest {
  private String name;
  private String password;
}
