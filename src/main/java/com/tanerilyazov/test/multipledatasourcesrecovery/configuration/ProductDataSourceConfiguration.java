package com.tanerilyazov.test.multipledatasourcesrecovery.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    @Bean(name = "productEntityManager")
    public EntityManager entityManager(@Qualifier("productEntityManagerFactory") EntityManagerFactory productEntityManagerFactory) {
        return productEntityManagerFactory.createEntityManager();
    }

    @Bean(name = "productEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("productDataSource") DataSource productDataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.tanerilyazov.test.multipledatasourcesrecovery.domain");
        factory.setDataSource(productDataSource);
        factory.setPersistenceUnitName("product");
        return factory;
    }

    @Bean(name = "productTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("productEntityManagerFactory") EntityManagerFactory productEntityManagerFactory) {
        return new JpaTransactionManager(productEntityManagerFactory);
    }
}
