package com.keenan.Tickets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author keenan on 27/03/2018
 */
@Controller
public class ErrorController {
    @GetMapping("/403")
    public String unauthorized() {
        return "error/403";
    }

    @GetMapping("/404")
    public String notFound() {
        return "error/404";
    }

    @GetMapping("/error")
    public String serverError() {
        return "error/500";
    }
}
