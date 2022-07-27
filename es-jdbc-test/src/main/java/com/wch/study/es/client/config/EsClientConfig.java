package com.wch.study.es.client.config;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/7/1 9:45
 */
public class EsClientConfig {
    private static final String DRIVER_CLASS_NAME = "org.elasticsearch.xpack.sql.jdbc.EsDriver";
    private static final String JDBC_PREFIX_URL = "jdbc:es://";

    public static HikariDataSource hikariDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();

        hikariDataSource.setDriverClassName(DRIVER_CLASS_NAME);
        hikariDataSource.setJdbcUrl(JDBC_PREFIX_URL + "172.16.10.130:9200");
        hikariDataSource.setUsername("elastic");
        hikariDataSource.setPassword("123456");
        hikariDataSource.setConnectionTestQuery(null);
        hikariDataSource.setReadOnly(true);
        return hikariDataSource;
    }
}
