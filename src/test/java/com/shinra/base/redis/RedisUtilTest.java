package com.shinra.base.redis;

import com.shinra.base.common.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Author yekai
 * @Date 2018/12/17 16:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTest {

    private static final String KEY="task";

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void push() {
        User user=new User("aa","1123","key","code","123");
        redisUtil.lPush(KEY,user);
    }

    @Test
    public void pop() {
        List<Object> messages=redisUtil.rPop(KEY,0, TimeUnit.MILLISECONDS);
        messages.forEach(message->{
            System.out.println(message);
        });

    }
}