package com.shinra.base.rabbit_mq.server.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * RabbitMq Ack
 * 通过实现ReturnCallBack接口
 * @author hexin
 * @date 2018/9/19 下午11:58
 */
@Service
public class FaountSenderService implements RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplatel;

    public void send(){
        String context="你好现在是:"+ LocalDateTime.now().toString()+"";
        System.out.println("HelloSender发送内容:"+context);
        //设置回调方法
        this.rabbitTemplatel.setReturnCallback(this);
        this.rabbitTemplatel.setConfirmCallback((correlationData, ack, cause) ->{
            if (!ack){
                System.out.println("HelloSender发送消息失败"+cause+correlationData);
            }else {
                System.out.println("HelloSender发送消息成功");
            }
        });
        this.rabbitTemplatel.convertAndSend("hello",context);
    }



    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("sender return success "+message.toString()+"==="+i+"==="+s+"==="+"s1"+"==="+"s2");
    }
}
