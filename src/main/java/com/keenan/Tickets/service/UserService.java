package com.keenan.Tickets.service;

import com.keenan.Tickets.model.User;
import com.keenan.Tickets.util.ResultMessage;
import com.keenan.Tickets.vo.RegisterUserVO;

/**
 * @author keenan on 28/01/2018
 */
public interface UserService {
    ResultMessage signUp(RegisterUserVO registerUserVO);

    ResultMessage checkMail(String email, String code);

    User getCurrentUser();
}
