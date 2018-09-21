package com.shinra.base.rabbit_mq.server.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FaountSenderServiceTest {
    @Autowired
    private FanoutSenderService fanoutSenderService;
    @Test
    public void send() {
        fanoutSenderService.send();
    }
}