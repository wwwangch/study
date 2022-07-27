package com.wch.study.es.client;

import com.wch.study.es.client.config.EsClientConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.elasticsearch.xpack.sql.jdbc.EsDriver;

import java.sql.*;
import java.util.Properties;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/30 11:13
 */
public class ConfigTest {
    public static void main(String[] args) throws SQLException {
        String url="jdbc:es://172.16.10.130:9300?user=elastic&password=123456";
        String url2="jdbc:es://localhost:9300?user=elastic&password=123456";
        Properties properties=new Properties();
//        properties.setProperty("user","elastic");
//        properties.setProperty("password","123456");
//        Connection connection1 = DriverManager.getConnection(url2);

        HikariDataSource hikariDataSource = EsClientConfig.hikariDataSource();
        Connection connection = hikariDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select 1");
        try {

        ResultSet resultSet = preparedStatement.executeQuery();
        }catch (SQLInvalidAuthorizationSpecException e){

        }

        System.out.println(11);

    }
}
