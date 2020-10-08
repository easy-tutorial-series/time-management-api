package com.github.gcnyin.mybatisdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class User {
  private Integer id;
  private String name;
  @JsonIgnore
  private String password;
  private List<Authority> authorities;
}
