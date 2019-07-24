package com.shinra.base.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author qiaoxin
 * @date 2019/7/24
 */
@Service
public class IndicatorService {

    private static final Logger LOG = LoggerFactory.getLogger(IndicatorService.class);


    @KafkaListener(topics = "#{kafkaTopicName}", groupId = "#{topicGroupId}")
    public void processMessage(ConsumerRecord<Integer, String> record) {
        LOG.info("kafka processMessage start");
        LOG.info("processMessage, topic = {}, msg = {}", record.topic(), record.value());

        // do something ...

        LOG.info("kafka processMessage end");
    }

}
