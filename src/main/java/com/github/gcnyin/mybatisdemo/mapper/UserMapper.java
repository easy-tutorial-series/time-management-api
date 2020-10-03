package com.github.gcnyin.mybatisdemo.mapper;

import com.github.gcnyin.mybatisdemo.model.Card;
import com.github.gcnyin.mybatisdemo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
  @Select("select id, name from user")
  List<User> findAll();

  @Select("select id, name from user where id = #{id}")
  @Results(value = {
    @Result(property = "id", column = "id", id = true),
    @Result(property = "name", column = "name"),
    @Result(property = "cards", column = "id", javaType = List.class, many = @Many(select = "findCardsByUserId"))
  })
  User findById(@Param("id") Integer id);

  @Insert("insert into user(name, password) values (#{name}, #{password})")
  int create(@Param(("name")) String name, @Param("password") String password);

  @Select("select id, content, user_id as userId from card where user_id = #{user_id}")
  List<Card> findCardsByUserId(@Param("userId") Integer userId);
}
