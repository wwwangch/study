package com.wch.study.es8.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/5/27 14:40
 */
@Data
@Accessors(chain = true)
public class Dict {

    /**
     * 字典标签
     */
    private String dictLabel;
    /**
     * 字典描述
     */
    private String dictValue;


    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}
