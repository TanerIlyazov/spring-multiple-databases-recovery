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
public class QueryUsersPeriodically {

    private final Logger log = Logger.getLogger(QueryUsersPeriodically.class.getName());

    @Autowired
    @Qualifier("userEntityManager")
    private EntityManager userEntityManager;

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void queryUsers() {
        List<String> users = userEntityManager.createQuery("SELECT name FROM User").getResultList();
        log.info("Queried users, result: " + users);
    }

}
