package com.tanerilyazov.test.multipledatasourcesrecovery.bean;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueryUsersPeriodically {

  private final EntityManager userEntityManager;

  public QueryUsersPeriodically(@Qualifier("userEntityManager") EntityManager userEntityManager) {
    this.userEntityManager = userEntityManager;
  }

  @Scheduled(initialDelay = 5000, fixedDelay = 30000)
  public void queryUsers() {
    List<String> userNames = userEntityManager.createQuery("SELECT name FROM User").getResultList();
    log.info("Queried users, result: {}", userNames);
  }
}
