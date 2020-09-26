package com.github.gcnyin.mybatisdemo.request;

import lombok.Data;

@Data
public class CreateUser {
  private String name;
  private String password;
}
