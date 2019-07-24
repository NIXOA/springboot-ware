package com.shinra.base.quartz.service.impl;

import com.shinra.base.common.User;
import com.shinra.base.quartz.Util.QuartzInitVopVosFactory;
import com.shinra.base.quartz.service.QuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 定时器相关的实现
 * @Author yekai
 * @Date 2018/9/30 14:38
 */
public class QuartzServiceImpl implements QuartzService {
    @Autowired
    private Scheduler scheduler;


    @Override
    public void initVopVos() {
        User job=new User("aaa","123456","SORT","cron","test");
        try{
            TriggerKey triggerKey=TriggerKey.triggerKey(job.getKey());
            //获取triggerKey
            CronTrigger trigger= (CronTrigger) scheduler.getTrigger(triggerKey);
            if (null==trigger){
                JobDetail jobDetail = JobBuilder.newJob(QuartzInitVopVosFactory.class)
                        .withIdentity(job.getJobName(), job.getJobGroup()).build();
                jobDetail.getJobDataMap().put("job", job);
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 * 15 * * ?");
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 * 15 * * ?");
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().startAt(new Date()).withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();
            /*scheduler.rescheduleJob如果服务器当前时间与你的表达式配置的执行时间差在两小时以内时，
            /动态修改就会出现立即执行的情况。所以这里设置执行时间从当前时间开始
            重新获取JobDataMap，并且更新参数*/
            JobDataMap jobDataMap = trigger.getJobDataMap();
            jobDataMap.put("job", job);

            //按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
