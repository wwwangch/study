package com.wch.study.easyexcel.entity;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/7/13 14:58
 */


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/4/20 8:57
 * 大气透过率
 */
@Data
public class MoxingTouguolv {

    private String _id;

    private String moxingId;


    @ExcelProperty(index = 0)
    private String zhuangbeiXinghao;


    @ExcelProperty(index = 1)
    private String kuoxiangMoxing;


    @ExcelProperty(index = 2)
    private Double nengjiandu;


    @ExcelProperty(index = 3)
    private Double fasheYangjiao;


    @ExcelProperty(index = 4)
    private Double touguolv;
}

