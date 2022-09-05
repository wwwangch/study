package com.wch.study.es8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/5/27 14:48
 */
@SpringBootApplication(scanBasePackages = {"com.wch.study"})
public class Es8TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(Es8TestApplication.class, args);
    }
}
