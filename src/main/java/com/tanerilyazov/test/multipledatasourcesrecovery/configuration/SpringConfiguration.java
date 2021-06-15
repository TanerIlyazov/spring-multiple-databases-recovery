package com.tanerilyazov.test.multipledatasourcesrecovery.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@Configuration
public class SpringConfiguration {
}
