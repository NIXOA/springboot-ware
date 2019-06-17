package com.shinra.base.rabbit.mq.client.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author hexin
 * @date 2018/9/16 下午10:29
 */
@Component
@RabbitListener(queues = "topic.messages")
public class TopicReceiver2 {

    @RabbitHandler
    public void process(String msg){
        System.out.println("TopicReceiver2 :"+msg);
    }
}
