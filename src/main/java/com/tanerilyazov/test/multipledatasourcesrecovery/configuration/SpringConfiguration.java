package com.tanerilyazov.test.multipledatasourcesrecovery.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.tanerilyazov.test.multipledatasourcesrecovery.domain")
@Configuration
public class SpringConfiguration {
}
