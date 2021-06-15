package com.tanerilyazov.test.multipledatasourcesrecovery.bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class QueryProductsPeriodically {

    private final EntityManager entityManager;

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void queryUsers() {
        List<String> products = entityManager.createQuery("SELECT name FROM Product").getResultList();
        log.info("Queried products, result: {}", products);
    }


}
