package com.keenan.Tickets.service;

import com.keenan.Tickets.model.User;
import com.keenan.Tickets.util.ResultMessage;

/**
 * @author keenan on 28/01/2018
 */
public interface UserService {
//    ResultMessage signIn(String email, String password);

    ResultMessage signUp(String username, String password, String email);

    ResultMessage checkMail(String email, String code);

    User getCurrentUser();
}
