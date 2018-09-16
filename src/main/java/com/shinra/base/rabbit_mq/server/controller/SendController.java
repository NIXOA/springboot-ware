package com.shinra.base.rabbit_mq.server.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息发送controller
 * @author hexin
 * @date 2018/9/16 下午8:42
 */
@RestController
public class SendController {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping("/rabbitSend")
    private String send(){
        StringBuilder times=new StringBuilder();
        for (int i = 0; i < 10; i++) {
            long time=System.nanoTime();
            this.amqpTemplate.convertAndSend("mqTest1","第"+i+"次发送的时间"+time);
            this.amqpTemplate.convertAndSend("mqTest2","第"+i+"次发送的时间"+time);
            times.append(time+"<br>");
        }
        return times.toString();
    }

    @RequestMapping("/topicSend1")
    public String send1(){
        String context="my topic 1";
        System.out.println("发送者说: "+context);
        this.amqpTemplate.convertAndSend("exchange","topic.message",context);
        return context;
    }

    @RequestMapping("/topicSend2")
    public String send2(){
        String context="my topic 2";
        System.out.println("发送者说: "+context);
        this.amqpTemplate.convertAndSend("exchange","topic.messages",context);
        return context;
    }
}
