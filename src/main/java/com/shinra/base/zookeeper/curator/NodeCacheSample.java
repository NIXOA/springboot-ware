package com.shinra.base.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 事件监听
 * @Author Godzilla
 * @Date 2019/6/19 14:30
 */
public class NodeCacheSample {
    private static final String PATH="/zk-book/nodecache";
    private static final String URL = "47.96.89.221:2182";

    public static void main(String[] args) throws Exception {
        CuratorFramework client=createClient(URL);
        client.start();
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(PATH,"init".getBytes());
        final NodeCache cache=new NodeCache(client,PATH,true);
        cache.getListenable().addListener(()-> System.out.println("Node data update,new data:"+new String(cache.getCurrentData().getData())));
        client.setData().forPath(PATH,"u".getBytes());
        Thread.sleep(1000);
        client.delete().deletingChildrenIfNeeded().forPath(PATH);
        Thread.sleep(Integer.MAX_VALUE);

    }


    /**
     * 创建连接
     */
    static CuratorFramework createClient(String url) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //使用fluent风格创建连接
        return CuratorFrameworkFactory.builder().connectString(url)
                .sessionTimeoutMs(5000).connectionTimeoutMs(3000)
                .retryPolicy(retryPolicy).build();
    }

}
