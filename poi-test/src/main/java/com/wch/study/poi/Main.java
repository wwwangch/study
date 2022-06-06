package com.wch.study.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/6 10:15
 */
public class Main {
    public static void main(String[] args) throws IOException {
        XWPFDocument xwpfDocument=new XWPFDocument(new FileInputStream("D:\\wch\\work\\文档\\中台\\综合情况_2022年5月31日.docx"));
        System.out.println(11);
    }
}
