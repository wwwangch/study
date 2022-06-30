package com.wch.study.es.client;

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
        String url="jdbc:es://https://my-deployment-c6fd57.es.us-central1.gcp.cloud.es.io:80?user=elastic&password=lrrHAzom8rAwUAYScP55Qd7r";
        Properties properties=new Properties();
//        properties.setProperty("user","elastic");
//        properties.setProperty("password","123456");
        Connection connection = DriverManager.getConnection(url, properties);
        PreparedStatement preparedStatement = connection.prepareStatement("select 1");
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println(11);
    }
}
