package com.wch.study.curator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/17 9:44
 */
@SpringBootApplication(scanBasePackages = {"com.wch.study.curator"})
public class CuratorTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CuratorTestApplication.class, args);
    }
}
