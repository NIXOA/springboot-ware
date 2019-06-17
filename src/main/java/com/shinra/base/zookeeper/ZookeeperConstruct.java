package com.shinra.base.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**最基本的Zookeeper会话实例
 * @Author Godzilla
 * @Date 2019/6/13 17:16
 */
public class ZookeeperConstruct implements Watcher {
    private static CountDownLatch connectedSemaphore=new CountDownLatch(1);

    public static void main(String[] args) throws IOException {
        ZooKeeper zooKeeper=new ZooKeeper("47.96.89.221:2182",5000,new ZookeeperConstruct());
        System.out.println(zooKeeper.getState());
        try {
            connectedSemaphore.await();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Zookeeper session established");
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched event ： "+watchedEvent);
        if (Event.KeeperState.SyncConnected==watchedEvent.getState()){
            connectedSemaphore.countDown();
        }
    }
}
