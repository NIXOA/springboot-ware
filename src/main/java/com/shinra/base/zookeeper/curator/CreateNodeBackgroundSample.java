package com.shinra.base.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Curator异步接口
 * @Author Godzilla
 * @Date 2019/6/19 10:56
 */
public class CreateNodeBackgroundSample {
    private static final String URL = "47.96.89.221:2182";
    private static final String PATH = "/zk-book/c1";
    private static CuratorFramework client;
    private static CountDownLatch semaphore=new CountDownLatch(2);
    private static ExecutorService executorService= Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws Exception {
        client=CreateSessionSample.createClient(URL);
        client.start();
        System.out.println("Main thread :"+Thread.currentThread().getName());
        //传入executor
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground((curatorFramework, curatorEvent) -> {
            System.out.println("event[code:"+curatorEvent.getResultCode()+"],type:"+curatorEvent.getType()+"]");
            System.out.println("Thread of processResult:"+Thread.currentThread().getName());
            semaphore.countDown();
        },executorService).forPath(PATH,"init".getBytes());

        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(((curatorFramework, curatorEvent) -> {
            System.out.println("event[code:"+curatorEvent.getResultCode()+"],type:"+curatorEvent.getType()+"]");
            System.out.println("Thread of processResult:"+Thread.currentThread().getName());
            semaphore.countDown();
        })).forPath(PATH,"init".getBytes());
        semaphore.await();
        executorService.shutdown();
    }




}
