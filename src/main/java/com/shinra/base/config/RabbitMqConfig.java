package com.shinra.base.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq的配置，发送者和接收者的名称必须一致，否则接收不到消息。
 * directed模式
 * @author hexin
 * @date 2018/9/16 下午8:39
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue queuel(){
        return new Queue("mqTest1");
    }
    @Bean
    public Queue queuel2(){
        return new Queue("mqTest2");
    }
}
