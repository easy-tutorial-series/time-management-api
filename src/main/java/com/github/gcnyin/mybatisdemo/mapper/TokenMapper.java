package com.github.gcnyin.mybatisdemo.mapper;

import com.github.gcnyin.mybatisdemo.model.Token;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenMapper {
  @Insert("insert into token(id, user_id) values (#{tokenId}, #{userId})")
  void createToken(@Param("tokenId") String tokenId, @Param("userId") Integer userId);

  @Select("select id, user_id as userId from token where id = #{tokenId}")
  Token findById(@Param("tokenId") String tokenId);

  @Update("update token set last_modified_date = current_timestamp where id = #{tokenId}")
  void touchToken(@Param("tokenId") String tokenId);

  @Delete("delete from token where id = #{tokenId}")
  void deleteToken(@Param("tokenId") String tokenId);
}
