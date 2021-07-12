package com.tanerilyazov.test.multipledatasourcesrecovery.bean;

import com.tanerilyazov.test.multipledatasourcesrecovery.domain.user.User;
import com.tanerilyazov.test.multipledatasourcesrecovery.mapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Component
public class QueryUsersPeriodically {

    private final Logger log = Logger.getLogger(QueryProductsPeriodically.class.getName());

    @Autowired
    @Qualifier("userJdbcTemplate")
    private JdbcTemplate userJdbcTemplate;

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void queryUsers() {
        List<User> users = userJdbcTemplate.query("select * from User", new UserRowMapper());
        log.info("Queried users, result: " + users);
    }

}
