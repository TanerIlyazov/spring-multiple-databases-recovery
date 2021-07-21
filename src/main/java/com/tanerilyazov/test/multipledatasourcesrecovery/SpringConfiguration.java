package com.tanerilyazov.test.multipledatasourcesrecovery;

import com.tanerilyazov.test.multipledatasourcesrecovery.domain.product.Product;
import com.tanerilyazov.test.multipledatasourcesrecovery.domain.user.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletException;
import java.util.logging.Logger;

@EnableJpaRepositories
@EnableScheduling
@Configuration
public class SpringConfiguration implements WebApplicationInitializer {

    private final Logger log = Logger.getLogger(SpringConfiguration.class.getName());

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        log.info("Initialized Spring app");
    }
}
