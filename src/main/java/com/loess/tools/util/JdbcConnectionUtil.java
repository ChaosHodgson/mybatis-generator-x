package com.loess.tools.util;

import com.loess.tools.config.Parameters;
import com.raddle.jdbc.JdbcTemplate;
import com.raddle.jdbc.datasource.DriverManagerDataSource;

public class JdbcConnectionUtil {

    // 数据库配置
    public static JdbcTemplate createJdbcTemplate() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(Parameters.DRIVER);
        ds.setUrl(Parameters.URL);
        ds.setUsername(Parameters.USERNAME);
        ds.setPassword(Parameters.PASSWORD);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        return jdbcTemplate;
    }
}
