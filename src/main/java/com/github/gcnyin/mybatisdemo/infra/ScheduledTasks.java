package com.github.gcnyin.mybatisdemo.infra;

import com.github.gcnyin.mybatisdemo.mapper.TokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledTasks {
  private final TokenMapper tokenMapper;

  public ScheduledTasks(TokenMapper tokenMapper) {
    this.tokenMapper = tokenMapper;
  }

  @Scheduled(fixedRate = 60_000)
  public void clearTokens() {
    tokenMapper.clearTokens();
    log.info("clear tokens");
  }
}
