package com.wch.study.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/17 13:44
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CuratorTestApplication.class})
public class TreeCacheTest {

    @Autowired
    private CuratorFramework client;

    @Test
    public void treeCache() throws Exception {

        final String path = "/treeCache";
        client.create().orSetData().withMode(CreateMode.PERSISTENT).forPath(path, "root".getBytes());
        Thread.sleep(1000);
        final TreeCache cache = new TreeCache(client, path);
        cache.start();


        cache.getListenable().addListener((client1, event) -> {
            switch (event.getType()) {
                case NODE_ADDED:
                    System.out.println("NODE_ADDED:" + event.getData().getPath());
                    break;
                case NODE_REMOVED:
                    System.out.println("NODE_REMOVED:" + event.getData().getPath());
                    break;
                case NODE_UPDATED:
                    System.out.println("NODE_UPDATED:" + event.getData().getPath());
                    break;
                case CONNECTION_LOST:
                    System.out.println("CONNECTION_LOST:" + event.getData().getPath());
                    break;
                case CONNECTION_RECONNECTED:
                    System.out.println("CONNECTION_RECONNECTED:" + event.getData().getPath());
                    break;
                case CONNECTION_SUSPENDED:
                    System.out.println("CONNECTION_SUSPENDED:" + event.getData().getPath());
                    break;
                case INITIALIZED:
                    System.out.println("INITIALIZED:" + event.getData().getPath());
                    break;
                default:
                    break;
            }
        });


        client.create().withMode(CreateMode.PERSISTENT).forPath(path + "/c1");
        Thread.sleep(1000);

        client.setData().forPath(path, "test".getBytes());
        Thread.sleep(1000);

        client.delete().forPath(path + "/c1");
        Thread.sleep(1000);

        client.delete().forPath(path);
        Thread.sleep(1000);
    }
}
