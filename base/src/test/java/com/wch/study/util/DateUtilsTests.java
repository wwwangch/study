package com.wch.study.util;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * @author ch w
 * @version 1.0
 * @since 2022/9/13 11:13
 */
public class DateUtilsTests {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void unsafeTest() {
        ArrayList<CompletableFuture> completableFutures = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            completableFutures.add(CompletableFuture.runAsync(() -> {
                try {
                    System.out.println(sdf.parse("2022-9-13 11:11:11"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }));
        }
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
    }

    @Test
    public void sateTest() {
        ArrayList<CompletableFuture> completableFutures = Lists.newArrayList();

        for (int i = 0; i < 10; i++) {
            completableFutures.add(CompletableFuture.runAsync(() -> {
                try {
                    System.out.println(DateSafeUtils.parse("2022-9-13 11:11:11", "yyyy-MM-dd HH:mm:ss"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }));
        }
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
    }
}
