package com.github.gcnyin.mybatisdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.gcnyin.mybatisdemo.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
