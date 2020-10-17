package com.github.gcnyin.mybatisdemo.mapper;

import com.github.gcnyin.mybatisdemo.model.Authority;
import com.github.gcnyin.mybatisdemo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
  @Select("select id, name from user")
  @Results(
    value = {
      @Result(column = "id", property = "id", id = true),
      @Result(column = "name", property = "name"),
      @Result(property = "authorities", column = "id", javaType = List.class, many = @Many(select = "findAuthoritiesByUsername"))
    }
  )
  List<User> findAll();

  @Select("select id, name, password from user where name = #{name}")
  @Results(
    value = {
      @Result(column = "id", property = "id", id = true),
      @Result(column = "name", property = "name"),
      @Result(column = "password", property = "password"),
      @Result(property = "authorities", column = "id", javaType = List.class, many = @Many(select = "findAuthoritiesByUsername"))
    }
  )
  User findByName(@Param("name") String name);

  @Select("select user_id as userId, authority from authority where user_id = #{userId}")
  List<Authority> findAuthoritiesByUsername(@Param("userId") String userId);
}
