package com.github.gcnyin.mybatisdemo.controller;

import com.github.gcnyin.mybatisdemo.dao.CardDao;
import com.github.gcnyin.mybatisdemo.model.Card;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {
  private final CardDao cardDao;

  public CardController(CardDao cardDao) {
    this.cardDao = cardDao;
  }

  @GetMapping("{id}")
  public Card findById(@PathVariable String id) {
    return cardDao.selectCardById(id);
  }
}
