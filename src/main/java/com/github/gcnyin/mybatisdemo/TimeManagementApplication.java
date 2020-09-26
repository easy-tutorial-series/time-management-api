package com.github.gcnyin.mybatisdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.gcnyin.mybatisdemo.dao")
public class TimeManagementApplication {
  public static void main(String[] args) {
    SpringApplication.run(TimeManagementApplication.class, args);
  }
}
