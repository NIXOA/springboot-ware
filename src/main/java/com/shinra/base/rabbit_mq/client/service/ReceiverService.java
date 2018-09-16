package com.shinra.base.rabbit_mq.client.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 消息接收服务类
 * @author hexin
 * @date 2018/9/16 下午8:47
 */
@Service
@RabbitListener(queues = "mqTest1")
public class ReceiverService {

    @RabbitHandler
    public void receiver(String msg){
        System.out.println("mqTest1 receiver:"+msg);
    }

}
