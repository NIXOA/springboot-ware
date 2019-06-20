package com.shinra.base.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Master选举
 * @Author Godzilla
 * @Date 2019/6/19 15:41
 */
public class MasterSelector {
    private static final String PATH="/curator/recipes_master_path";
    private static final String URL = "47.96.89.221:2182";

    public static void main(String[] args) throws InterruptedException {
        CuratorFramework client=createClient(URL);
        client.start();
        LeaderSelector selector=new LeaderSelector(client, PATH, new LeaderSelectorListenerAdapter() {
            /**
             * curator在成功获取master权利的时候
             * 回调此方法，实现业务逻辑
             * @param curatorFramework
             * @throws Exception
             */
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                System.out.println("成为master角色");
                Thread.sleep(3000);
                System.out.println("完成master操作，释放master权利");
            }
        });
        selector.autoRequeue();
        selector.start();
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
