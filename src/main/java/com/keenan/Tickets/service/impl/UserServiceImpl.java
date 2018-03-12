package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.model.SysRole;
import com.keenan.Tickets.model.User;
import com.keenan.Tickets.repository.SysRoleRepository;
import com.keenan.Tickets.repository.UserRepository;
import com.keenan.Tickets.service.UserService;
import com.keenan.Tickets.util.MailUtil;
import com.keenan.Tickets.util.ResultMessage;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keenan on 09/02/2018
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private MailUtil mailUtil;

    @Override
    public ResultMessage signIn(String email, String password) {
        return null;
    }

    @Override
    public ResultMessage signUp(String username, String password, String email) {
        if (!email.matches("^\\w+@(\\w+\\.)+\\w+$")) {
            return new ResultMessage(ResultMessage.ERROR, "邮箱格式不合法!");
        }

        if (userRepository.findUserByUsername(username) != null) {
            return new ResultMessage(ResultMessage.ERROR, "该用户名已被注册!");
        }

        if (userRepository.findUserByEmail(email) != null) {
            return new ResultMessage(ResultMessage.ERROR, "该邮箱已被注册!");
        }


        String code = MD5Encoder.encode((email + System.currentTimeMillis()).getBytes());
        List<SysRole> roles = new ArrayList<>();
        roles.add(sysRoleRepository.findSysRoleByName("ROLE_USER"));
        User user = new User(username, password, email, true, false, 0.0, 1, 0.0, code, roles);

        if (!mailUtil.sendRegisterMail(email, code)) {
            return new ResultMessage(ResultMessage.ERROR, "发送验证邮件失败!");
        } else {
            userRepository.save(user);
            return new ResultMessage(ResultMessage.SUCCESS, "已发送验证邮箱，请点击邮箱中链接激活账号");
        }
    }

    @Override
    public ResultMessage checkMail(String email, String code) {
        User user = userRepository.findUserByEmail(email);
        if (user.getCode() != null && user.getCode().equals(code)) {
            user.setConfirmed(true);
            userRepository.save(user);
            return new ResultMessage(ResultMessage.SUCCESS, "验证成功");
        }
        return new ResultMessage(ResultMessage.ERROR, "验证失败");
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            return (User) auth.getPrincipal();
        } else {
            return null;
        }
    }
}
