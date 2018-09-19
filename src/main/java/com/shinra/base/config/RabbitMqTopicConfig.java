package com.shinra.base.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Topic Exchange配置类
 * @author hexin
 * @date 2018/9/16 下午9:53
 */
@Configuration
public class RabbitMqTopicConfig {
    /**
     * 只接收一个topic
     */
    private final static String MESSAGE="topic.message";
    /**
     * 接收多个topic
     */
    private final static String MESSSAGES="topic.messages";
    @Bean
    public Queue queueMessage(){
        return new Queue(RabbitMqTopicConfig.MESSAGE);
    }
    @Bean
    public Queue queueMessages(){
        return new Queue(RabbitMqTopicConfig.MESSSAGES);
    }
    @Bean
    TopicExchange exchange(){
        return new TopicExchange("exchange");
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage,TopicExchange exchange){
        return  BindingBuilder.bind(queueMessage).to(exchange).with("#topic.message");
    }
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages,TopicExchange exchange){
        return BindingBuilder.bind(queueMessages).to(exchange).with("#topic.#");
    }
}
