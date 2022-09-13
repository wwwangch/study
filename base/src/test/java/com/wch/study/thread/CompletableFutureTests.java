package com.wch.study.thread;

import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ch w
 * @version 1.0
 * @since 2022/9/13 14:17
 */
public class CompletableFutureTests {
    private static ThreadPoolExecutor threadPoolExecutor = null;
    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    @BeforeAll
    public static void before() {
        threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(), r -> new Thread(r, "线程-" + atomicInteger.getAndIncrement()));
    }


}
