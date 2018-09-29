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
public class FanoutSenderService implements RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplatel;


    /**
     * 消息发送到Exchange之后确认已发送，触发回调。
     */
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
        this.rabbitTemplatel.convertAndSend("fanoutExchange"," ",context);
    }

    /**
     * 通过实现ReturnCallback接口，如果消息从交换器发送到对应队列失败时触发
     * （比如根据发送消息时指定的routingKey找不到队列时会触发）
     */
    @Override
    public void returnedMessage(Message message, int replayCode, String replayText, String exchange, String routingKey) {
        System.out.println("sender return success "+message.toString()+"==="+replayCode+"==="+replayText+"==="+exchange+"==="+routingKey);
    }
}
