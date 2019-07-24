package com.shinra.base.config.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author qiaoxin
 * @date 2019/7/24
 */
@ConfigurationProperties("kafka.topic")
public class KafkaTopicProperties {

    private String groupId;
    private String[] topicName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String[] getTopicName() {
        return topicName;
    }

    public void setTopicName(String[] topicName) {
        this.topicName = topicName;
    }
}
