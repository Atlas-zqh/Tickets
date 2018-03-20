package com.keenan.Tickets.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author keenan on 10/02/2018
 */
@Component
public class MailUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${mail.from.addr}")
    private String from;

    public boolean sendRegisterMail(String email, String code) {

        MimeMessage message = mailSender.createMimeMessage();
        String registerLink = "http://localhost:8080/user/confirm?email=" + email + "&amp;code=" + code;


        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("Tickets验证邮件");
            helper.setText(registerLink, true);

            mailSender.send(message);
            logger.info("发送邮件成功");
            return true;
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常", e);
            return false;
        }
    }
}
