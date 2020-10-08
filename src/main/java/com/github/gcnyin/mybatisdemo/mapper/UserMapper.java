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
      @Result(column = "id", property = "id"),
      @Result(column = "name", property = "name"),
      @Result(property = "authorities", column = "id", javaType = List.class, many = @Many(select = "findAuthoritiesByUsername"))
    }
  )
  List<User> findAllUsers();

  @Select("select id, name, password from user where name = #{name}")
  @Results(
    value = {
      @Result(column = "id", property = "id"),
      @Result(column = "name", property = "name"),
      @Result(column = "password", property = "password"),
      @Result(property = "authorities", column = "id", javaType = List.class, many = @Many(select = "findAuthoritiesByUsername"))
    }
  )
  User findByName(@Param("name") String name);

  @Select("select id from user where name = #{name} limit 1")
  Integer findIdByName(@Param("name") String name);

  @Insert("insert into user(name, password) values (#{name}, #{password})")
  int create(@Param(("name")) String name, @Param("password") String password);

  @Select("select count(1) from user where name = #{name}")
  int countByName(@Param("name") String name);

  @Select("select user_id as userId, authority from authority where user_id = #{userId}")
  List<Authority> findAuthoritiesByUsername(@Param("userId") String userId);
}
