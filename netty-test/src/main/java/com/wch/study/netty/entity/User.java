package com.wch.study.netty.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/16 13:27
 */
@Data
public class User {
    private String id;

    private String username;

    private String password;

    private Date date;
}
