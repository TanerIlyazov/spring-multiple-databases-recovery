package com.tanerilyazov.test.multipledatasourcesrecovery.bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class QueryProductsPeriodically {

    @Autowired
    @Qualifier("productEntityManager")
    private EntityManager productEntityManager;

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void queryUsers() {
        List<String> products = productEntityManager.createQuery("SELECT name FROM Product").getResultList();
        log.info("Queried products, result: {}", products);
    }


}
