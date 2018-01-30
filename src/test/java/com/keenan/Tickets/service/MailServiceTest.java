package com.keenan.Tickets.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author keenan on 28/01/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {
    @Autowired
    private MailService mailService;

    @Test
    public void testSendSimpleMail() {
        mailService.sendSimpleMail("151250206@smail.nju.edu.cn", "Hello", "Hello");
    }
}
