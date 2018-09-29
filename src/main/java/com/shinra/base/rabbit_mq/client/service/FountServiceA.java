package com.shinra.base.rabbit_mq.client.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author hexin
 * @date 2018/9/20 上午12:16
 */
@Service
@RabbitListener(queues = "fanout-A")
public class FountServiceA {
    @RabbitHandler
    public void process(String hello, Channel channel, Message message) throws IOException {
        System.out.println("HelloReceiverA收到 ： "+hello+" 收到时间 "+ LocalDateTime.now());
        try {
            //消息成功接收，返回ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("receiver success!");
        }catch (IOException exception){
            exception.printStackTrace();
            //接收失败，丢弃这条消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            System.out.println("receiver fail");
        }
    }

}
