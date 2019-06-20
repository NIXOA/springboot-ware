package com.shinra.base.zookeeper.curator;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 分布式锁
 * @Author Godzilla
 * @Date 2019/6/19 16:19
 */
public class RecipesLock {
    public static final String LOCK_PATH="/curator_recipes_lock_path";
    private static final String URL = "47.96.89.221:2182";

    public static void main(String[] args) {
        CuratorFramework client=createClient(URL);
        client.start();
        final InterProcessLock lock=new InterProcessMutex(client,LOCK_PATH);
        final CountDownLatch latch=new CountDownLatch(1);
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    lock.acquire();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:sss|SSS");
                String orderNo=sdf.format(new Date());
                System.out.println("生成的订单号是:"+orderNo);
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            latch.countDown();
        }


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
