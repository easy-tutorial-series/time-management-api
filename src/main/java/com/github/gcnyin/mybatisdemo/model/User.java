package com.github.gcnyin.mybatisdemo.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
  private Integer id;
  private String name;
  private String password;
  private List<Card> cards;
}
