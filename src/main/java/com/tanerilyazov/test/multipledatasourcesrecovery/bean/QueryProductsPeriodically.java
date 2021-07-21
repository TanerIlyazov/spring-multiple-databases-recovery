package com.tanerilyazov.test.multipledatasourcesrecovery.bean;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Component
public class QueryProductsPeriodically {

    private final Logger log = Logger.getLogger(QueryProductsPeriodically.class.getName());

    @Autowired
    @Qualifier("productEntityManager")
    private EntityManager productEntityManager;

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void queryUsers() {
        List<String> products = productEntityManager.createQuery("SELECT name FROM Product").getResultList();
        log.info("Queried products, result: " + products);
    }


}
