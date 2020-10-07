package com.github.gcnyin.mybatisdemo.mapper;

import com.github.gcnyin.mybatisdemo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
  @Select("select id, name from user where name = #{name}")
  User findByName(@Param("name") String name);

  @Insert("insert into user(name, password) values (#{name}, #{password})")
  int create(@Param(("name")) String name, @Param("password") String password);

  @Select("select count(1) from user where name = #{name}")
  int countByName(@Param("name") String name);
}
