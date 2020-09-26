package com.github.gcnyin.mybatisdemo.dao;

import com.github.gcnyin.mybatisdemo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
  @Select("SELECT * FROM user")
  List<User> findAll();
}
