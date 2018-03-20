package com.keenan.Tickets.controller;

import com.keenan.Tickets.service.UserService;
import com.keenan.Tickets.util.ResultMessage;
import com.keenan.Tickets.vo.RegisterUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author keenan on 10/02/2018
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String signUp(@ModelAttribute("user") RegisterUserVO user, BindingResult bindingResult, Model model) throws Exception {
        System.out.println(user.toString());

        if (user.getUserType().equals("ROLE_USER")) {
            ResultMessage resultMessage = userService.signUp(user);

            if (resultMessage.getResultCode().equals(ResultMessage.ERROR)) {
                model.addAttribute("result", resultMessage);
                model.addAttribute("user", user);
                return "signUp";
            } else {
                model.addAttribute("result", resultMessage);
                model.addAttribute("user", new RegisterUserVO());
                model.addAttribute("checkMail", "请前往注册邮箱进行认证，点击邮件中链接完成认证");
                return "login";
            }
        } else {
            return "403";
        }
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String displaySignUp(Model model) {
        model.addAttribute("user", new RegisterUserVO());
        model.addAttribute("result", new ResultMessage(ResultMessage.SUCCESS));
        return "signUp";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String displayConfirm(@RequestParam(value = "email", required = true) String email,
                                 @RequestParam(value = "code", required = true) String code,
                                 Model model) {
        ResultMessage resultMessage = userService.checkMail(email, code);
        if (resultMessage.getResultCode().equals(ResultMessage.ERROR)) {
            return "confirmFailure";
        } else {
            return "confirmSuccess";
        }
    }

    @RequestMapping(value = "/events")
    public String index(Model model) {
        return "user/events";
    }


}
