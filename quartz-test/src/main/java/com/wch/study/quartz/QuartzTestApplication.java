package com.wch.study.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/5/27 15:45
 */
@SpringBootApplication(scanBasePackages = {"com.wch.study"})
public class QuartzTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuartzTestApplication.class, args);
    }
}
