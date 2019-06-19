package com.shinra.base.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 使用curator创建会话
 *
 * @Author Godzilla
 * @Date 2019/6/18 16:24
 */
public class CreateSessionSample {
    private static final String URL = "47.96.89.221:2182";
    private static final String PATH = "/zk-book/c1";
    private static CuratorFramework client;

    public static void main(String[] args) throws Exception {
        client=createClient(null,URL);
        client.start();
        createNode(PATH,"123");
        System.out.println(getNodeData(client,PATH));

    }


    /**
     * 创建连接
     */
    static CuratorFramework createClient(String nameSpace, String url) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //使用fluent风格创建连接
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(url)
                .sessionTimeoutMs(5000).connectionTimeoutMs(3000)
                .retryPolicy(retryPolicy).namespace(nameSpace).build();
        //client.start();
        return CuratorFrameworkFactory.newClient(url, 5000, 3000, retryPolicy);
    }


    /**
     * 创建节点
     *
     * @param path
     * @param data
     * @throws Exception
     */
    private static void createNode(String path, String data) throws Exception {
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, data.getBytes());
    }


    /**
     * 删除节点
     *
     * @param client
     * @param path
     */
    private static void deleteNode(CuratorFramework client, String path) throws Exception {
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        client.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(path);
    }


    /**
     * 获取节点的数据
     * @param client
     * @param path
     * @return
     * @throws Exception
     */
    private static String getNodeData(CuratorFramework client, String path) throws Exception {
        Stat stat=new Stat();
        //读取一个节点的数据，同时获取到该节点的stat
        return new String(client.getData().storingStatIn(stat).forPath(path));
    }


    /**
     * 更新节点的数据
     * @param client
     * @param path
     * @param data
     */
    private static void updateNodeData(CuratorFramework client, String path,String data) throws Exception {
        Stat stat=new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        System.out.println("Success set node for"+path+",new version:"+client.setData().withVersion(stat.getVersion()).forPath(path));
        try {
            client.setData().withVersion(stat.getVersion()).forPath(path);
        }catch (Exception e){
            System.out.println("Fail set node due to "+e.getMessage());
        }

    }




}
