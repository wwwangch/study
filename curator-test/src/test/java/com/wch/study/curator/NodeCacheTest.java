package com.wch.study.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/17 9:49
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CuratorTestApplication.class)
public class NodeCacheTest {

    @Autowired
    private CuratorFramework client;

    @Test
    public void nodeCache() throws Exception {
        final String path = "/nodecache";


//        client.delete()
//                .deletingChildrenIfNeeded()
//                .forPath(path);

        client.create().orSetData().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, "wwwwcccghgh".getBytes());

        NodeCache nodeCache = new NodeCache(client, path);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(() -> System.out.println("node data change , new data is " + new String(nodeCache.getCurrentData().getData())));

        client.setData().forPath(path, "111".getBytes());
        client.setData().forPath(path, "222".getBytes());
        client.create().orSetData().creatingParentsIfNeeded().forPath(path + "/qq", "333".getBytes());

        Thread.sleep(1000);
    }
}
