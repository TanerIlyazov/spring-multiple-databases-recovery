package com.tanerilyazov.test.multipledatasourcesrecovery.bean;

import com.tanerilyazov.test.multipledatasourcesrecovery.domain.product.Product;
import com.tanerilyazov.test.multipledatasourcesrecovery.mapper.ProductRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Component
public class QueryProductsPeriodically {

    private final Logger log = Logger.getLogger(QueryProductsPeriodically.class.getName());

    @Autowired
    @Qualifier("productJdbcTemplate")
    private JdbcTemplate productJdbcTemplate;

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void queryUsers() {
        log.info("Querying products");
        List<Product> products = productJdbcTemplate.query("select * from Product", new ProductRowMapper());
        log.info("Queried products, result: " + products);
    }


}
