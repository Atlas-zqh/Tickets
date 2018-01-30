package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.service.MailService;
import com.keenan.Tickets.util.ResultMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author keenan on 28/01/2018
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Value("${mail.from.addr}")
    private String from;

    /**
     * 发送邮件
     *
     * @param to
     * @param content
     * @param subject
     * @return
     */
    @Override
    public ResultMessage sendSimpleMail(String to, String content, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        System.out.println("message = " + message);
        message.setFrom(from);
        System.out.println("from = " + from);
        message.setTo(to);
        System.out.println("to = " + to);
        message.setText(content);
        System.out.println("content = " + content);
        message.setSubject(subject);
        System.out.println("subject = " + subject);

        try {
            mailSender.send(message);
            System.out.println("message = " + message);
            logger.info("简单邮件已发送");
        } catch (Exception e) {
            logger.error("邮件发送时异常", e);
        }
        return ResultMessage.Success;
    }
}
