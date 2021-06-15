package com.tanerilyazov.test.multipledatasourcesrecovery.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.tanerilyazov.test.multipledatasourcesrecovery.domain.product",
        entityManagerFactoryRef = "productEntityManager",
        transactionManagerRef = "productTransactionManager"
)
public class ProductDatasourceConfiguration {

    @Bean(name = "productDataSource")
    @ConfigurationProperties(prefix = "spring.product.datasource")
    public DataSource dataSource(@Qualifier("productDataSourceProperties") DataSourceProperties productDataSourceProperties) {
        return productDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "productEntityManager")
    public EntityManager entityManager(@Qualifier("productEntityManagerFactory") EntityManagerFactory productEntityManagerFactory) {
        return productEntityManagerFactory.createEntityManager();
    }

    @Bean(name = "productEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired EntityManagerFactoryBuilder builder, @Qualifier("productDataSource") DataSource productDataSource) {
        return builder
                .dataSource(productDataSource)
                .packages("com.tanerilyazov.test.multipledatasourcesrecovery.domain.product")
                .persistenceUnit("product")
                .build();
    }

    @Bean(name = "productTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("productEntityManagerFactory") EntityManagerFactory productEntityManagerFactory) {
        return new JpaTransactionManager(productEntityManagerFactory);
    }

    @Bean(name = "productDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.product.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

}
