package com.github.gcnyin.mybatisdemo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityMapper {
  @Insert("insert into authority(user_id, authority) values(#{userId}, 'ROLE_USER')")
  void createDefaultAuthority(@Param("userId") Integer userId);
}
