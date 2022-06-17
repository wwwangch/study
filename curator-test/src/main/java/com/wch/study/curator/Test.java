package com.wch.study.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/16 17:23
 */
public class Test {
    public static void main(String[] args) throws Exception {
        nodeCache();


    }

    public static void nodeCache() throws Exception {
        final String path="/nodecache";
        CuratorFramework client = getClient();
        client.start();

        Thread.sleep(1000);

//        client.delete()
//                .deletingChildrenIfNeeded()
//                .forPath(path);

        client.create().orSetData().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,"wwwwcccghgh".getBytes());

        NodeCache nodeCache = new NodeCache(client, path);

        nodeCache.getListenable().addListener(()-> System.out.println("node data change , new data is "+new String(nodeCache.getCurrentData().getData())));

        client.setData().forPath(path,"111".getBytes());
        client.setData().forPath(path,"222".getBytes());

        Thread.sleep(1000);

        client.close();

    }

    public static CuratorFramework getClient(){
        RetryPolicy retryPolicy=new ExponentialBackoffRetry(60,3,300);
        return CuratorFrameworkFactory.builder()
                .connectString("172.16.10.197:2181")
                .namespace("wch")
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(15*1000)
                .sessionTimeoutMs(60*1000)
                .build();
    }
}
