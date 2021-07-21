package com.tanerilyazov.test.multipledatasourcesrecovery.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    @Primary
    @Bean(name = "userEntityManager")
    public EntityManager entityManager(@Qualifier("userEntityManagerFactory") EntityManagerFactory userEntityManagerFactory) {
        return userEntityManagerFactory.createEntityManager();
    }

    @Primary
    @Bean(name = "userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("userDataSource") DataSource userDataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.tanerilyazov.test.multipledatasourcesrecovery.domain.user");
        factory.setDataSource(userDataSource);
        factory.setPersistenceUnitName("user");
        return factory;
    }

    @Primary
    @Bean(name = "userTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("userEntityManagerFactory") EntityManagerFactory userEntityManagerFactory) {
        return new JpaTransactionManager(userEntityManagerFactory);
    }

}
