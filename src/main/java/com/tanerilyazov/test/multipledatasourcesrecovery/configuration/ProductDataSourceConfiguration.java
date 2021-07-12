package com.tanerilyazov.test.multipledatasourcesrecovery.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class ProductDataSourceConfiguration {

    @Value("${spring.product.datasource.url}")
    private String productDataSourceUrl;

    @Value("${spring.product.datasource.username}")
    private String productDataSourceUsername;

    @Value("${spring.product.datasource.password}")
    private String productDataSourcePassword;

    @Value("${spring.product.datasource.driver-class-name}")
    private String productDataSourceDriverClassName;

    @Bean
    public DataSource productDataSource() {
        System.out.println("initializing product datasource");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(productDataSourceDriverClassName);
        dataSource.setUrl(productDataSourceUrl);
        dataSource.setUsername(productDataSourceUsername);
        dataSource.setPassword(productDataSourcePassword);

        return dataSource;
    }

    @Bean(name = "productJdbcTemplate")
    public JdbcTemplate productJdbcTemplate(@Qualifier("productDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
