package com.haozi.masterslave.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author hao.yang
 * @date 2019/7/19
 */
@Repository
public class UserRepository {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate primaryJdbcTemplate;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate secondaryJdbcTemplate;


    public void save() {
        primaryJdbcTemplate.execute("insert into users(1111) values('1111')");
    }

    public Integer queryCount() {
        return secondaryJdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }
}
