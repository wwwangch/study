package com.wch.study.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.wch.study.easyexcel.entity.MoxingTouguolv;
import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.io.FilenameUtils;

import java.util.List;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/7/13 14:23
 */
public class ReadTest {
    public static void main(String[] args) {
        String filePath="D:\\wch\\work\\文档\\508\\透过率.xlsx";
        List<MoxingTouguolv> moxingTouguolvs = EasyExcel.read(filePath).sheet(0).head(MoxingTouguolv.class).doReadSync();
        System.out.println(FilenameUtils.getExtension("qqq.txt"));
        System.out.println(111);
    }
}
