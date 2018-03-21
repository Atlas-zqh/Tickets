package com.keenan.Tickets.bean;

import com.keenan.Tickets.model.User;

/**
 * @author keenan on 21/03/2018
 */
public class UserInfoBean {
    public Long id;

    public String username;

    public String password;

    public String email;

    public Boolean isValid;

    public Boolean isConfirmed;

    public Double points;

    public Integer level;

    public Double balance;

    private UserInfoBean(Long id, String username, String password, String email, Boolean isValid, Boolean isConfirmed, Double points, Integer level, Double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isValid = isValid;
        this.isConfirmed = isConfirmed;
        this.points = points;
        this.level = level;
        this.balance = balance;
    }

    public UserInfoBean(User user) {
        this(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getValid(), user.getConfirmed(), user.getPoints(), user.getLevel(), user.getBalance());
    }

    public UserInfoBean() {
    }
}
