package com.keenan.Tickets.controller;

import com.keenan.Tickets.bean.ShowPlanBriefBean;
import com.keenan.Tickets.service.ShowPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author keenan on 21/03/2018
 */
@Controller
@RequestMapping("/user/event")
public class EventController {
    @Autowired
    private ShowPlanService showPlanService;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String displayEvents(Model model) {
        List<ShowPlanBriefBean> showPlanBriefBeans = showPlanService.getAllShowPlanBriefBeansBeforeToday();
        model.addAttribute("showPlanBriefBeans", showPlanBriefBeans);
        return "user/events";
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String displayEventDetail(@RequestParam(value = "id", required = true) String id, Model model) {
        model.addAttribute("eventDetail", showPlanService.getShowPlanByID(Long.valueOf(id)));
        return "user/eventDetail";
    }
}
