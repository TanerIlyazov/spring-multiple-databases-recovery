package com.tanerilyazov.test.multipledatasourcesrecovery;

import com.tanerilyazov.test.multipledatasourcesrecovery.bean.QueryProductsPeriodically;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletException;
import java.util.logging.Logger;

@EnableScheduling
@Configuration
public class SpringConfiguration implements WebApplicationInitializer {

    private final Logger log = Logger.getLogger(QueryProductsPeriodically.class.getName());

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        log.info("Started Spring app");
    }
}
