package com.shinra.base.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * zookeeper对象复用 sessionId和sessionPassWord
 *
 * @Author Godzilla
 * @Date 2019/6/17 15:06
 */
public class ZookeeperConstructWithSID implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("47.96.89.221:2182", 5000, new ZookeeperConstructWithSID());
        connectedSemaphore.await();
        System.out.println("zooKeeper="+zooKeeper);
        long sessionId = zooKeeper.getSessionId();
        byte[] password = zooKeeper.getSessionPasswd();
        ZooKeeper zooKeeper2 = new ZooKeeper("47.96.89.221:2182  d", 5000, new ZookeeperConstructWithSID(), sessionId, password);
        System.out.println("zooKeeper2="+zooKeeper2);
        Thread.sleep(Integer.MAX_VALUE);

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched event ： " + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            connectedSemaphore.countDown();
        }

    }
}
