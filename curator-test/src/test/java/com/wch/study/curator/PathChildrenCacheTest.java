package com.wch.study.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/17 10:42
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CuratorTestApplication.class})
public class PathChildrenCacheTest {

    @Autowired
    private CuratorFramework client;

    @Test
    public void pathChildrenCache() throws Exception {
        final String path = "/pathChildrenCache";

//        client.create().orSetData().creatingParentsIfNeeded().forPath(path, "pathCache".getBytes());
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        pathChildrenCache.getListenable().addListener(((client1, event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    System.out.println("CHILD_ADDED:" + event.getData().getPath());
                    break;
                case CHILD_REMOVED:
                    System.out.println("CHILD_REMOVED:" + event.getData().getPath());
                    break;
                case CHILD_UPDATED:
                    System.out.println("CHILD_UPDATED:" + event.getData().getPath());
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
        }));

        client.create().orSetData().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path + "/c1", "".getBytes());
        Thread.sleep(1000);

        client.setData().forPath(path + "/c1", "c1value".getBytes());
        Thread.sleep(1000);

        client.setData().forPath(path + "/c1", "c1valuev2".getBytes());
        Thread.sleep(1000);

        client.delete().forPath(path + "/c1");
        Thread.sleep(1000);

        client.delete().forPath(path); //监听节点本身的变化不会通知
        Thread.sleep(1000);

    }
}
