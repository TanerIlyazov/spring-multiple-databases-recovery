package com.tanerilyazov.test.multipledatasourcesrecovery.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class UserDataSourceConfiguration {

    @Value("${spring.user.datasource.url}")
    private String userDataSourceUrl;

    @Value("${spring.user.datasource.username}")
    private String userDataSourceUsername;

    @Value("${spring.user.datasource.password}")
    private String userDataSourcePassword;

    @Value("${spring.user.datasource.driver-class-name}")
    private String userDataSourceDriverClassName;


    @Bean
    @Primary
    public DataSource userDataSource() {
        System.out.println("initializing user datasource");
        HikariConfig userHikariConfig = new HikariConfig();
        userHikariConfig.setJdbcUrl(userDataSourceUrl);
        userHikariConfig.setUsername(userDataSourceUsername);
        userHikariConfig.setPassword(userDataSourcePassword);
        userHikariConfig.setDriverClassName(userDataSourceDriverClassName);
        return new HikariDataSource(userHikariConfig);
    }

    @Bean(name = "userJdbcTemplate")
    public JdbcTemplate userJdbcTemplate(@Qualifier("userDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

}
