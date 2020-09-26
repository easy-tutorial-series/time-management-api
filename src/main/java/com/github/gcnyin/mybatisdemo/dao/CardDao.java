package com.github.gcnyin.mybatisdemo.dao;

import com.github.gcnyin.mybatisdemo.model.Card;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

@Component
public class CardDao {
  private final SqlSession sqlSession;

  public CardDao(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public Card selectCardById(String id) {
    return sqlSession.selectOne("selectCardById", id);
  }
}
