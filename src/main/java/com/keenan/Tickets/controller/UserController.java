package com.keenan.Tickets.controller;

import com.keenan.Tickets.model.User;
import com.keenan.Tickets.security.UserService;
import com.keenan.Tickets.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author keenan on 10/02/2018
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/events")
    public String index(Model model) {
        return "user/events";
    }

}
