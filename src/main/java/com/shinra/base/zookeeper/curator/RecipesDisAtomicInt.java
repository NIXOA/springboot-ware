package com.shinra.base.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**分布式计数器
 * @Author Godzilla
 * @Date 2019/6/20 10:01
 */
public class RecipesDisAtomicInt {
    private static final String URL = "47.96.89.221:2182";
    private static final String PATH="/curator_recipes_distatomicint_path";
    public static void main(String[] args) throws Exception {
        CuratorFramework client=createClient(URL);
        client.start();
        DistributedAtomicInteger atomicInteger=new DistributedAtomicInteger(client,PATH,new RetryNTimes(3,1000));
        AtomicValue<Integer> rc=atomicInteger.add(8);
        System.out.println("Result:"+rc.succeeded());
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
