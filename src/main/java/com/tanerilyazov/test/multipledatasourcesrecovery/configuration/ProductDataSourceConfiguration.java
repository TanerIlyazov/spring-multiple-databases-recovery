package com.tanerilyazov.test.multipledatasourcesrecovery.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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
        HikariConfig productHikariConfig = new HikariConfig();
        productHikariConfig.setJdbcUrl(productDataSourceUrl);
        productHikariConfig.setUsername(productDataSourceUsername);
        productHikariConfig.setPassword(productDataSourcePassword);
        productHikariConfig.setDriverClassName(productDataSourceDriverClassName);
        return new HikariDataSource(productHikariConfig);
    }

    @Bean(name = "productJdbcTemplate")
    public JdbcTemplate productJdbcTemplate(@Qualifier("productDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
