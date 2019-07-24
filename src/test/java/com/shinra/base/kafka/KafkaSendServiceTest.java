package com.shinra.base.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaSendServiceTest {

    @Autowired
    private SendService sendService;

    @Test
    public void sendMessage() {
        String topic = "topic1";
        String data = "test1";
        sendService.sendMessage(topic,data);
    }
}