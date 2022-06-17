package com.wch.study.curator.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/17 9:45
 */
@Configuration
public class CuratorConfig {

    @Bean(destroyMethod = "close")
    public CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(60, 3, 300);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("172.16.10.197:2181")
                .namespace("wch")
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(15 * 1000)
                .sessionTimeoutMs(60 * 1000)
                .build();
        curatorFramework.start();
        return curatorFramework;
    }
}
