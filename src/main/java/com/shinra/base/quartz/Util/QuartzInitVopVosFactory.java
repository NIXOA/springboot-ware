package com.shinra.base.quartz.Util;

import com.shinra.base.common.User;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 实现定时任务调度的具体处理过程
 * @Author yekai
 * @Date 2018/9/30 14:20
 */
@DisallowConcurrentExecution
public class QuartzInitVopVosFactory implements Job {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //所有的参数以及其他信息都在JobExecutionContext
       //如果没有JobFactory 这个类，在这里是没办法注入任何类的
        User user= (User) jobExecutionContext.getMergedJobDataMap().get("job");
        System.out.println(user);
    }
}
