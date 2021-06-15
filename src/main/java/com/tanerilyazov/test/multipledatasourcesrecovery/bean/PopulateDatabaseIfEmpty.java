package com.tanerilyazov.test.multipledatasourcesrecovery.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class PopulateDatabaseIfEmpty {

    private final EntityManager userEntityManager;

    private final EntityManager productEntityManager;

    @Autowired
    public PopulateDatabaseIfEmpty(EntityManager userEntityManager, @Qualifier("productEntityManager") EntityManager productEntityManager) {
        this.userEntityManager = userEntityManager;
        this.productEntityManager = productEntityManager;
        initializeDb();
    }

    public void initializeDb() {
        long usersCount = (long) userEntityManager.createQuery("SELECT count(*) FROM User").getSingleResult();
        long productCount = (long) productEntityManager.createQuery("SELECT count(*) FROM Product").getSingleResult();


        List<Integer> integerList = new Random().ints(100,1000).limit(100).boxed().collect(Collectors.toList());
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

    private void insertUser(Integer randomValue) {
        userEntityManager.getTransaction().begin();
        userEntityManager.createNativeQuery("INSERT INTO User(name, email, age) values (:name, :email, :age)")
                .setParameter("name", "pesho-" + randomValue)
                .setParameter("email", "pesho-" + randomValue + "@gmail.com")
                .setParameter("age", randomValue % 10)
                .executeUpdate();
        userEntityManager.getTransaction().commit();
    }

    private void insertProduct(Integer randomValue) {
        productEntityManager.getTransaction().begin();
        productEntityManager.createNativeQuery("INSERT INTO Product(name, price) values (:name, :price)")
                .setParameter("name", "product-" + randomValue)
                .setParameter("price", randomValue.doubleValue())
                .executeUpdate();
        productEntityManager.getTransaction().commit();
    }
}
