package com.shinra.base.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 在QuartzConfiguration注入AutowireCapableBeanFactory
 * @Author yekai
 * @Date 2018/9/30 14:14
 */
@Component
public class JobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    protected Object createJobInstatnce(TriggerFiredBundle bundle) throws Exception {
        //调用父类的方法
        Object jobInstance =super.createJobInstance(bundle);
        //进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

}
