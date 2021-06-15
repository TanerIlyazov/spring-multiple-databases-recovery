package com.tanerilyazov.test.multipledatasourcesrecovery.bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Component
public class PopulateDatabaseIfEmpty {

    private final EntityManager entityManager;

    /**
     * For testing purposes, we want it to run only once and not be handled
     * by an event listener to trigger transaction correctly
     */
    @Scheduled(initialDelay = 3000, fixedDelay = 9999999)
    public void initializeDb() {

        long usersCount = (long) entityManager.createQuery("SELECT count(*) FROM User").getSingleResult();
        long productCount = (long) entityManager.createQuery("SELECT count(*) FROM Product").getSingleResult();

        List<Integer> integerList = new Random().ints(100, 1000).limit(100).boxed().collect(Collectors.toList());
        if (usersCount == 0) {
            log.info("Populating users database");
            integerList.forEach(
                    this::insertUser
            );
        }
        if (productCount == 0) {
            log.info("Populating products database");
            integerList.forEach(
                    this::insertProduct
            );

        }
    }

    public void insertUser(Integer randomValue) {
        entityManager.createNativeQuery("INSERT INTO User(name, email, age) values (:name, :email, :age)")
                .setParameter("name", "pesho-" + randomValue)
                .setParameter("email", "pesho-" + randomValue + "@gmail.com")
                .setParameter("age", randomValue % 10)
                .executeUpdate();
    }

    public void insertProduct(Integer randomValue) {
        entityManager.createNativeQuery("INSERT INTO Product(name, price) values (:name, :price)")
                .setParameter("name", "product-" + randomValue)
                .setParameter("price", randomValue.doubleValue())
                .executeUpdate();
    }
}
