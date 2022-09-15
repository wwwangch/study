package com.wch.study.thread;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SynchronousQueue;
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

    /**
     * 异步运行，不带返回值
     *
     * @throws InterruptedException
     */
    @Test
    public void runAsyncTest() throws InterruptedException {
        CompletableFuture.runAsync(() -> System.out.println(getCurrentThreadName() + "CompletableFuture runASync method"), threadPoolExecutor);
        Thread.sleep(1000);
    }

    /**
     * 异步运行，带返回值
     *
     * @throws InterruptedException
     */
    @Test
    public void supplyAsyncTest() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> getCurrentThreadName() + "CompletableFuture supplyASync method", threadPoolExecutor)
                .thenAccept(System.out::println);
        Thread.sleep(1000);
    }

    /**
     * 上一个任务完成后用上一个任务的线程执行下一个任务
     * @throws InterruptedException
     */
    @Test
    public void thenRunTest() throws InterruptedException {
        CompletableFuture.runAsync(() -> System.out.println(getCurrentThreadName() + "CompletableFuture runASync method"), threadPoolExecutor)
                .thenRun(() -> System.out.println(getCurrentThreadName() + "CompletableFuture thenRun method"));
        Thread.sleep(1000);
    }


    /**
     * 上一个任务完成后异步执行下一个任务
     * @throws InterruptedException
     */
    @Test
    public void thenRunAsyncTest() throws InterruptedException {
        CompletableFuture.runAsync(() -> System.out.println(getCurrentThreadName() + "CompletableFuture runASync method"), threadPoolExecutor)
                .thenRunAsync(() -> System.out.println(getCurrentThreadName() + "CompletableFuture thenRun method"),threadPoolExecutor);
        Thread.sleep(1000);
    }

    

    private String getCurrentThreadName() {
        return "线程名称:" + Thread.currentThread().getName() + " ";
    }


}
