package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.model.SysRole;
import com.keenan.Tickets.model.User;
import com.keenan.Tickets.repository.SysRoleRepository;
import com.keenan.Tickets.repository.UserRepository;
import com.keenan.Tickets.service.UserService;
import com.keenan.Tickets.util.MailUtil;
import com.keenan.Tickets.util.ResultMessage;
import com.keenan.Tickets.vo.RegisterUserVO;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import java.util.ArrayList;
import java.util.List;
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
    public ResultMessage signUp(RegisterUserVO registerUserVO) {
        if (userRepository.findUserByEmail(registerUserVO.getEmail()) != null) {
            return new ResultMessage(ResultMessage.ERROR, "该邮箱已被注册!");
        }

        if (userRepository.findUserByUsername(registerUserVO.getUsername()) != null) {
            return new ResultMessage(ResultMessage.ERROR, "该用户名已被注册!");
        }

        if (!registerUserVO.getEmail().matches("^\\w+@(\\w+\\.)+\\w+$")) {
            return new ResultMessage(ResultMessage.ERROR, "邮箱格式不合法!");
        }

        if (registerUserVO.getPassword().equals("") || registerUserVO.getConfirmPassword().equals("")) {
            return new ResultMessage(ResultMessage.ERROR, "请输入密码!");
        }

        if (!registerUserVO.getPassword().equals(registerUserVO.getConfirmPassword())) {
            return new ResultMessage(ResultMessage.ERROR, "两次输入的密码不一致!");
        }

        String code = UUID.randomUUID().toString().replace("-", "");
        SysRole role = sysRoleRepository.findSysRoleByName(registerUserVO.getUserType());
        User user = new User(registerUserVO.getUsername(), registerUserVO.getPassword(), registerUserVO.getEmail(), true, false, 0.0, 1, 0.0, code, role);

        if (!mailUtil.sendRegisterMail(registerUserVO.getEmail(), code)) {
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
