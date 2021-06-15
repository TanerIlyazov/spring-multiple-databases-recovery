package com.tanerilyazov.test.multipledatasourcesrecovery.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.tanerilyazov.test.multipledatasourcesrecovery.domain.user",
        entityManagerFactoryRef = "userEntityManager",
        transactionManagerRef = "userTransactionManager"
)
public class UserDatasourceConfiguration {

    @Primary
    @Bean(name = "userDataSource")
    @ConfigurationProperties(prefix = "spring.user.datasource")
    public DataSource dataSource(@Qualifier("userDataSourceProperties") DataSourceProperties userDataSourceProperties) {
        return userDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "userEntityManager")
    public EntityManager entityManager(@Qualifier("userEntityManagerFactory") EntityManagerFactory userEntityManagerFactory) {
        return userEntityManagerFactory.createEntityManager();
    }

    @Primary
    @Bean(name = "userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired EntityManagerFactoryBuilder builder, @Qualifier("userDataSource") DataSource userDataSource) {
        return builder
                .dataSource(userDataSource)
                .packages("com.tanerilyazov.test.multipledatasourcesrecovery.domain.user")
                .persistenceUnit("user")
                .build();
    }

    @Primary
    @Bean(name = "userTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("userEntityManagerFactory") EntityManagerFactory userEntityManagerFactory) {
        return new JpaTransactionManager(userEntityManagerFactory);
    }

    @Bean(name = "userDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.user.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

}
