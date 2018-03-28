package com.keenan.Tickets.controller;

import com.keenan.Tickets.bean.PasswordBean;
import com.keenan.Tickets.bean.RegisterBean;
import com.keenan.Tickets.bean.UserInfoBean;
import com.keenan.Tickets.model.User;
import com.keenan.Tickets.service.UserService;
import com.keenan.Tickets.service.VenueService;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author keenan on 10/02/2018
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private VenueService venueService;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String displaySignUp(Model model) {
        model.addAttribute("user", new RegisterBean());
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

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String displayUserInfo(Model model) throws Exception {
        User user = userService.getCurrentUser();
        UserInfoBean userInfoVO = new UserInfoBean(user);
        model.addAttribute("user", userInfoVO);
        return "user/userinfo";
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String displayEvents(Model model) throws Exception {
        return "user/events";
    }

    @RequestMapping(value = "/register.action", method = RequestMethod.POST)
    public String signUp(@ModelAttribute("user") RegisterBean registerBean, BindingResult bindingResult, Model model) throws Exception {
        System.out.println("registerBean = " + registerBean.toString());
        if (registerBean.getUserType().equals("ROLE_USER")) {
            ResultMessage resultMessage = userService.signUp(registerBean);

            if (resultMessage.getResultCode().equals(ResultMessage.ERROR)) {
                model.addAttribute("result", resultMessage);
                model.addAttribute("user", registerBean);
                return "signUp";
            } else {
                model.addAttribute("result", resultMessage);
                model.addAttribute("user", new RegisterBean());
                model.addAttribute("info", "请前往注册邮箱进行认证，点击邮件中链接完成认证");
                return "login";
            }
        } else if (registerBean.getUserType().equals("ROLE_VENUE")) {
            ResultMessage resultMessage = venueService.signUpVenue(registerBean);

            if (resultMessage.getResultCode().equals(ResultMessage.ERROR)) {
                model.addAttribute("result", resultMessage);
                model.addAttribute("user", registerBean);
                return "signUp";
            } else {
                model.addAttribute("result", resultMessage);
                model.addAttribute("user", new RegisterBean());
                model.addAttribute("info","场馆的登录码为"+resultMessage.getResultMessage()+", 请记住");
                return "login";
            }
        } else {
            return "error/403";
        }
    }

    @RequestMapping(value = "/modifyPassword.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage modifyPassword(@RequestBody PasswordBean passwordBean) {
        return userService.modifyPassword(passwordBean);
    }

}
