package com.github.gcnyin.mybatisdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.github.gcnyin.mybatisdemo.mapper")
public class TimeManagementApplication {
  public static void main(String[] args) {
    SpringApplication.run(TimeManagementApplication.class, args);
  }
}
