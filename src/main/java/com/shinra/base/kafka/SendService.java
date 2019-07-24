package com.shinra.base.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author qiaoxin
 * @date 2019/7/24
 */
@Service
public class SendService {

    private static final Logger LOG= LoggerFactory.getLogger(SendService.class);

    private final KafkaTemplate<Integer, String> kafkaTemplate;


    /**
     * 注入KafkaTemplate
     * @param kafkaTemplate
     */
    @Autowired
    public SendService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    public void sendMessage(String topic, String data) {
        LOG.info("kafka sendMessage start");
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                LOG.error("kafka sendMessage error, ex = {}, topic = {}, data = {}", ex, topic, data);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                LOG.info("kafka sendMessage success topic = {}, data = {}", topic, data);
            }
        });
        LOG.info("kafka sendMessage end");
    }
}
