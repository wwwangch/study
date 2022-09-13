package com.wch.study.es8.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Expert {

    private String id;


    private Integer number;
    //专家姓名
    private String expertName;
    //性别
    private String gender;
    //年龄
    private Integer age;

    private Date birthday;

    private String birthplace;
    //家庭住址
    private String address;
    //电话
    private String telephone;

    //证件类型
//    private String idCardType;
    //身份证号码
    private String idCard;

    //人员类型
//    private String personType;
    //工作单位
    private String workUnit;
    //职称
    private String jobTitle;
    //银行卡号
    private String bankCardNumber;
    //开户行
    private String accountBank;
    //专家组
    private String expertGroup;
    //工作状态
    private String workStatus;
    //头衔
    private String title;
    //职务
    private String duty;
    //个性
    private String peculiarity;
    //所属学部
    private String department;
    //研究领域
    private String researchField;
    //优先级
    private int priority;

    private Byte flag;


    private Date createTime;


    private String relation;

    public Expert() {
        this.priority = 1;
    }
}