package com.tanerilyazov.test.multipledatasourcesrecovery.bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class QueryUsersPeriodically {

    private final EntityManager userEntityManager;

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void queryUsers() {
        List<String> userNames = userEntityManager.createQuery("SELECT name FROM User").getResultList();
        log.info("Queried users, result: {}", userNames);
    }


}
