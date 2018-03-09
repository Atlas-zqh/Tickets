package com.keenan.Tickets.controller;

import com.keenan.Tickets.util.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author keenan on 10/02/2018
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/template")
    public String temp(Model model){
        model.addAttribute("host","www.baidu.com");
        return "temp";
    }
}
