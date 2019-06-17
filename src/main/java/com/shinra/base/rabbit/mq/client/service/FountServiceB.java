package com.shinra.base.rabbit.mq.client.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author hexin
 * @date 2018/9/21 下午11:32
 */
@Service
@RabbitListener(queues = "fanout-B")
public class FountServiceB {
    @RabbitHandler
    public void process(String hello, Channel channel, Message message) {
        System.out.println("HelloReceiverB收到 ： "+hello+" 收到时间 "+ LocalDateTime.now());
        try {
            //ack返回false，并重新回到队列
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("receiver success!");
        }catch (IOException exception){
            exception.printStackTrace();
            //丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            System.out.println("receiver fail");
        }
    }
}
