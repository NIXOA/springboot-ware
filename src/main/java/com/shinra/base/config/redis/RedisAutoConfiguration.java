package com.shinra.base.config.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * Redis配置类
 * @Author yekai
 * @Date 2018/9/27 15:47
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Object> template=new RedisTemplate<>();

        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
