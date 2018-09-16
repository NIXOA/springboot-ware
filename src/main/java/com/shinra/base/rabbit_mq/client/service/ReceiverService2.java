package com.shinra.base.rabbit_mq.client.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author hexin
 * @date 2018/9/16 下午9:20
 */
@Service
@RabbitListener(queues = "mqTest2")
public class ReceiverService2 {

    @RabbitHandler
    public void recevier(String msg){
        System.out.println("mqTest2 receiver2"+msg);
    }
}
