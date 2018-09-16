package com.shinra.base.rabbit_mq.client.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收topicMessage
 * @author hexin
 * @date 2018/9/16 下午10:28
 */
@Component
@RabbitListener(queues = "topic.message")
public class TopicReceivcer1 {
    @RabbitHandler
    public void process(String msg){
        System.out.println("TopicReceiver1 :"+msg);
    }
}
