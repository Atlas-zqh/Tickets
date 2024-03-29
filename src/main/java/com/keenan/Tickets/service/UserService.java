package com.keenan.Tickets.service;

import com.keenan.Tickets.bean.PasswordBean;
import com.keenan.Tickets.bean.RegisterBean;
import com.keenan.Tickets.model.User;
import com.keenan.Tickets.util.ResultMessage;

/**
 * @author keenan on 28/01/2018
 */
public interface UserService {
    ResultMessage signUp(RegisterBean registerBean);

    ResultMessage checkMail(String email, String code);

    User getCurrentUser();

    ResultMessage modifyPassword(PasswordBean passwordBean);
}
