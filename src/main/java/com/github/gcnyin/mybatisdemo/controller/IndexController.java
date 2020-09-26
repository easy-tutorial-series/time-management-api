package com.github.gcnyin.mybatisdemo.controller;

import com.github.gcnyin.mybatisdemo.dao.UserMapper;
import com.github.gcnyin.mybatisdemo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class IndexController {
    private final UserMapper userMapper;

    public IndexController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<User> userList() {
        return userMapper.findAll();
    }
}
