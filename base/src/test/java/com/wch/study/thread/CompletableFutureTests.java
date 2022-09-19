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
     */
    @Test
    public void runAsyncTest() {
        CompletableFuture.runAsync(() -> System.out.println(getCurrentThreadName() + "CompletableFuture runASync method"), threadPoolExecutor)
                .join();
    }

    /**
     * 异步运行，带返回值
     */
    @Test
    public void supplyAsyncTest() {
        CompletableFuture.supplyAsync(() -> getCurrentThreadName() + "CompletableFuture supplyASync method", threadPoolExecutor)
                .thenAccept(System.out::println)
                .join();
    }

    /**
     * 上一个任务完成后用上一个任务的线程执行下一个任务
     */
    @Test
    public void thenRunTest() {
        CompletableFuture.runAsync(() -> System.out.println(getCurrentThreadName() + "CompletableFuture runASync method"), threadPoolExecutor)
                .thenRun(() -> System.out.println(getCurrentThreadName() + "CompletableFuture thenRun method"))
                .join();
    }


    /**
     * 上一个任务完成后使用默认线程池异步执行下一个任务
     */
    @Test
    public void thenRunAsyncTest() {
        CompletableFuture.runAsync(() -> System.out.println(getCurrentThreadName() + "CompletableFuture runASync method"), threadPoolExecutor)
                .thenRunAsync(() -> System.out.println(getCurrentThreadName() + "CompletableFuture thenRun method"))
                .join();
    }

    /**
     * 上一个任务完成后使用默认线程池异步执行下一个任务
     * thenRunAsync不同参数区别为下一个任务的线程来源不同
     *
     * @throws InterruptedException
     */
    @Test
    public void thenRunAsyncWithCustomThreadPoolTest() throws InterruptedException {
        CompletableFuture.runAsync(() -> System.out.println(getCurrentThreadName() + "CompletableFuture runASync method"), threadPoolExecutor)
                .thenRunAsync(() -> System.out.println(getCurrentThreadName() + "CompletableFuture thenRun method"), threadPoolExecutor);
        Thread.sleep(1000);
    }

    @Test
    public void thenAcceptAsyncTest() {
        CompletableFuture.supplyAsync(() -> getCurrentThreadName() + "CompletableFuture supplyASync method", threadPoolExecutor)
                .thenAcceptAsync((str) -> System.out.println(getCurrentThreadName() + "\n" + str), threadPoolExecutor)
                .join();
    }

    /**
     * handle方法可以对上个任务的异常进行处理
     */
    @Test
    public void handleAsyncTest() {
//        CompletableFuture.supplyAsync(() -> getCurrentThreadName() + "CompletableFuture supplyASync method", threadPoolExecutor)
//                        .handleAsync((result,e)->{
//                            if (null==e){
//                                return "无异常";
//                            }else {
//                                e.printStackTrace();
//                                return "有异常";
//                            }
//                        },threadPoolExecutor)
//                .thenAccept(System.out::println);
        CompletableFuture.supplyAsync(() -> "23".substring(4), threadPoolExecutor)
                .handleAsync((result, e) -> {
                    if (null == e) {
                        return "无异常";
                    } else {
                        e.printStackTrace();
                        return "有异常";
                    }
                }, threadPoolExecutor)
                .thenAccept(System.out::println)
                .join();
    }

    @Test
    public void whenCompleteTest() {
        CompletableFuture<String> normal = CompletableFuture.supplyAsync(() -> getCurrentThreadName() + "supplyAsync method", threadPoolExecutor)
                .whenComplete((result, e) -> {
                    if (null != e) {
                        e.printStackTrace();
                    } else {
                        System.out.println(result);
                    }
                });
        CompletableFuture<String> exception = CompletableFuture.supplyAsync(() -> createException() + getCurrentThreadName() + "supplyAsync method with exception", threadPoolExecutor)
                .whenComplete((result, e) -> {
                    if (null != e) {
                        e.printStackTrace();
                    } else {
                        System.out.println(result);
                    }
                });
        CompletableFuture.allOf(normal, exception).join();
    }

    @Test
    public void thenAcceptBothAsyncTest() throws InterruptedException {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> getCurrentThreadName() + "CompletableFuture supplyASync method1", threadPoolExecutor);
        CompletableFuture.supplyAsync(() -> getCurrentThreadName() + "CompletableFuture supplyASync method2", threadPoolExecutor)
                .thenAcceptBothAsync(supplyAsync, (res1, res2) -> System.out.println(res1 + "\n" + res2));
        Thread.sleep(1000);
    }

    @Test
    public void thenApplyTest() {
        CompletableFuture.supplyAsync(() -> getCurrentThreadName() + "CompletableFuture supplyASync method", threadPoolExecutor)
                .thenApply((result) -> getCurrentThreadName() + "\n" + result)
                .thenAccept(System.out::println)
                .join();
    }

    @Test
    public void exceptionallyTest() {
        CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException();
        }).exceptionally((e) -> {
            e.printStackTrace();
            return "程序异常";
        }).thenAccept(System.out::println);
    }

    /**
     * 用来连接两个CompletableFuture，是生成一个新的CompletableFuture。
     */
    @Test
    public void thenComposeTest() {
        CompletableFuture.supplyAsync(() -> getCurrentThreadName() + "CompletableFuture supplyASync method", threadPoolExecutor)
                .thenCompose((str) -> CompletableFuture.runAsync(() -> System.out.println(str)))
                .join();
    }

    @Test
    public void thenCombineTest() {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> getCurrentThreadName() + "CompletableFuture supplyASync method", threadPoolExecutor);
        CompletableFuture<String> stringCompletableFuture2 = CompletableFuture.supplyAsync(() -> getCurrentThreadName() + "CompletableFuture supplyASync method-2", threadPoolExecutor);
        stringCompletableFuture.thenCombine(stringCompletableFuture2, (result1, result2) ->
                result1 + "\n" + result2 + "\n" + getCurrentThreadName()
        ).thenAccept(System.out::println);
    }


    private String getCurrentThreadName() {
        return "线程名称:" + Thread.currentThread().getName() + " ";
    }

    private String createException() {
        int i = 1, j = 0;
        System.out.println(i / j);
        return "";
    }


}
