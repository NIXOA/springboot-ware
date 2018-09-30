package com.shinra.base.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 订阅模式配置
 * @author hexin
 * @date 2018/9/19 下午11:50
 */
@Configuration
public class RabbitMqFanout {
    /**
     * durable设置为true，当服务重启的时候这个队列将会存活。
     * 也就是说队列被持久化了。同样的，交换机的durable也和队列同理。
     */
    @Bean
    public Queue queueA(){
        return new Queue("fanout-A");
    }
    @Bean
    public Queue queueB(){
        return new Queue("fanout-B");
    }

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeA(Queue queueA,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue queueB,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }
}
