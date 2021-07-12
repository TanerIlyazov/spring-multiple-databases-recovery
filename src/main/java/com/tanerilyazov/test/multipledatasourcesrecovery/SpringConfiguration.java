package com.tanerilyazov.test.multipledatasourcesrecovery;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletException;
import java.util.logging.Logger;

@EnableScheduling
@Configuration
public class SpringConfiguration implements WebApplicationInitializer {

    private final Logger log = Logger.getLogger(SpringConfiguration.class.getName());

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        log.info("Initialized Spring app");
    }
}
