package com.keenan.Tickets.service;

import com.keenan.Tickets.util.ResultMessage;

/**
 * @author keenan on 28/01/2018
 */
public interface MailService {
    /**
     * 发送邮件
     *
     * @param to
     * @param content
     * @param subject
     * @return
     */
    ResultMessage sendSimpleMail(String to, String content, String subject);
}
