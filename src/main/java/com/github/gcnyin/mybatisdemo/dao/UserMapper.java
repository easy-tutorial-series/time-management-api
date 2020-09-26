package com.github.gcnyin.mybatisdemo.dao;

import com.github.gcnyin.mybatisdemo.model.Card;
import com.github.gcnyin.mybatisdemo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends Mapper {
  @Select("select id, name from user")
  List<User> findAll();

  @Select("select id, name from user where id = #{id}")
  @Results(value = {
    @Result(property = "cards", column = "id", javaType = List.class, many = @Many(select = "findCardsByUserId"))
  })
  User findById(@Param("id") String id);

  @Insert("insert into user(name, password) values (#{name}, #{password})")
  int create(@Param(("name")) String name, @Param("password") String password);

  @Select("select id, content from card where user_id = #{user_id}")
  List<Card> findCardsByUserId(@Param("userId") String userId);
}
