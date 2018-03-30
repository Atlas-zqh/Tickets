package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.bean.PasswordBean;
import com.keenan.Tickets.bean.RegisterBean;
import com.keenan.Tickets.model.SysRole;
import com.keenan.Tickets.model.User;
import com.keenan.Tickets.repository.SysRoleRepository;
import com.keenan.Tickets.repository.UserRepository;
import com.keenan.Tickets.service.UserService;
import com.keenan.Tickets.util.MailUtil;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    public ResultMessage signUp(RegisterBean registerBean) {
        if (userRepository.findUserByEmail(registerBean.getEmail()) != null) {
            return new ResultMessage(ResultMessage.ERROR, "该邮箱已被注册!");
        }

        if (userRepository.findUserByUsername(registerBean.getUsername()) != null) {
            return new ResultMessage(ResultMessage.ERROR, "该用户名已被注册!");
        }

        if (!registerBean.getEmail().matches("^\\w+@(\\w+\\.)+\\w+$")) {
            return new ResultMessage(ResultMessage.ERROR, "邮箱格式不合法!");
        }

        if (registerBean.getPassword().equals("") || registerBean.getConfirmPassword().equals("")) {
            return new ResultMessage(ResultMessage.ERROR, "请输入密码!");
        }

        if (!registerBean.getPassword().equals(registerBean.getConfirmPassword())) {
            return new ResultMessage(ResultMessage.ERROR, "两次输入的密码不一致!");
        }

        String code = UUID.randomUUID().toString().replace("-", "");
        SysRole role = sysRoleRepository.findSysRoleByName(registerBean.getUserType());
        User user = new User(registerBean.getUsername(), registerBean.getPassword(), registerBean.getEmail(), true, false, 0.0, 1, 100000.0, "12345678", "12345678", code, role);

        if (!mailUtil.sendRegisterMail(registerBean.getEmail(), code)) {
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
            User user=(User)auth.getPrincipal();
            return userRepository.findFirstById(user.getId());
        } else {
            return null;
        }
    }

    @Override
    public ResultMessage modifyPassword(PasswordBean passwordBean) {
        User user = getCurrentUser();

        if (passwordBean.newPassword.equals("") || passwordBean.oldPassword.equals("") || passwordBean.confirmPassword.equals("")) {
            return new ResultMessage(ResultMessage.ERROR, "请输入密码");
        }

        if (!passwordBean.oldPassword.equals(user.getPassword())) {
            return new ResultMessage(ResultMessage.ERROR, "原密码错误");
        } else {
            if (!passwordBean.newPassword.equals(passwordBean.confirmPassword)) {
                return new ResultMessage(ResultMessage.ERROR, "两次密码输入不一致");
            }
        }
        user.setPassword(passwordBean.newPassword);
        userRepository.save(user);
        return new ResultMessage(ResultMessage.SUCCESS, "修改成功");
    }
}
