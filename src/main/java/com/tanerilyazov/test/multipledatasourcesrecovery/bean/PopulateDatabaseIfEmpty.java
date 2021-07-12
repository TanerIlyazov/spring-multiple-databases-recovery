package com.tanerilyazov.test.multipledatasourcesrecovery.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

@Component
public class PopulateDatabaseIfEmpty {

    private final Logger log = Logger.getLogger(QueryProductsPeriodically.class.getName());

    private final JdbcTemplate userJdbcTemplate;

    private final JdbcTemplate productJdbcTemplate;

    private final static String DROP_TABLE_USER = "DROP TABLE IF EXISTS User;";
    private final static String CREATE_TABLE_USER = "CREATE TABLE User (\n" +
            "id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
            "name VARCHAR(255),\n" +
            "email VARCHAR(255),\n" +
            "age bigint);";
    private final static String INSERT_DATA_USER = "INSERT INTO User(name, email, age)\n" +
            "VALUES ('pesho1', \"pesho1@gmail.com\", 18), ('pesho2', \"pesho2@gmail.com\", 18), ('pesho3', \"pesho3@gmail.com\", 18), ('pesho4', \"pesho4@gmail.com\", 18), ('pesho5', \"pesho5@gmail.com\", 18);";

    private final static String DROP_TABLE_PRODUCT = "DROP TABLE IF EXISTS Product;";
    private final static String CREATE_TABLE_PRODUCT = "CREATE TABLE Product (\n" +
            "id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
            "name VARCHAR(255),\n" +
            "price int);";
    private final static String INSERT_DATA_PRODUCT = "INSERT INTO Product(name, price)\n" +
            "VALUES ('product1', 18), ('product2', 18), ('product3', 18), ('product4', 18), ('product5', 18);";


    @Autowired
    public PopulateDatabaseIfEmpty(@Qualifier("userJdbcTemplate") JdbcTemplate userJdbcTemplate, @Qualifier("productJdbcTemplate") JdbcTemplate productJdbcTemplate) throws FileNotFoundException {
        this.userJdbcTemplate = userJdbcTemplate;
        this.productJdbcTemplate = productJdbcTemplate;
        initializeDb();
        log.info("Initialized databases");
    }

    public void initializeDb() {
        userJdbcTemplate.execute(DROP_TABLE_USER);
        userJdbcTemplate.execute(CREATE_TABLE_USER);
        userJdbcTemplate.execute(INSERT_DATA_USER);

        productJdbcTemplate.execute(DROP_TABLE_PRODUCT);
        productJdbcTemplate.execute(CREATE_TABLE_PRODUCT);
        productJdbcTemplate.execute(INSERT_DATA_PRODUCT);
    }
}
