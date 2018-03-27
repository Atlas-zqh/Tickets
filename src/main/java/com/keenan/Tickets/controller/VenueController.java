package com.keenan.Tickets.controller;

import com.alibaba.fastjson.JSON;
import com.keenan.Tickets.bean.*;
import com.keenan.Tickets.model.ShowPlan;
import com.keenan.Tickets.model.Venue;
import com.keenan.Tickets.model.util.ShowPlanStatus;
import com.keenan.Tickets.model.util.ShowPlanType;
import com.keenan.Tickets.service.ShowPlanService;
import com.keenan.Tickets.service.VenueService;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author keenan on 23/03/2018
 */
@Controller
@RequestMapping("/venue")
public class VenueController {
    @Autowired
    private VenueService venueService;
    @Autowired
    private ShowPlanService showPlanService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String displayVenueInfo(Model model) {
        Venue venue = venueService.getCurrentVenue();
        if (venue == null) {
            return "500";
        } else {
            String email = venueService.getVenueRegisterEmail(venue.getVenueId());
            VenueInfoBean venueInfoBean = new VenueInfoBean(venue, email);
//            System.out.println("venueInfoBean = " + (venueInfoBean.venueAddress == null));
            model.addAttribute("venueInfoBean", venueInfoBean);
            // 位置表
            model.addAttribute("seatsInfo", venueService.getSeatsInfo(venue));
            // 导航使用
            List<ShowPlanBriefBean> showPlanBriefBeans = showPlanService.getAllShowPlansByVenueAfterToday(venue);
            model.addAttribute("showPlanBriefBeans", showPlanBriefBeans);
        }
        return "venues/venueInfo";
    }

    @RequestMapping(value = "/showPlan", method = RequestMethod.GET)
    public String displayShowPlan(Model model) {
        Venue venue = venueService.getCurrentVenue();
        if (venue == null) {
            return "500";
        } else {
            List<ShowPlanBriefBean> showPlanBriefBeans = showPlanService.getAllShowPlansByVenueAfterToday(venue);
            model.addAttribute("showPlanBriefBeans", showPlanBriefBeans);
        }
        return "venues/showPlan";
    }

    @RequestMapping(value = "/newPlan", method = RequestMethod.GET)
    public String displayNewPlanForm(Model model) {
        Venue venue = venueService.getCurrentVenue();
        if (venue == null) {
            return "500";
        } else {
            // 初始化导航
            List<ShowPlanBriefBean> showPlanBriefBeans = showPlanService.getAllShowPlansByVenueAfterToday(venue);
            model.addAttribute("showPlanBriefBeans", showPlanBriefBeans);
            // 加入座位区域信息
            List<SeatInfoBean> seatInfoBeans = venueService.getSeatsInfo(venue);
            model.addAttribute("seatInfoBeans", seatInfoBeans);

            VenueInfoBean venueInfoBean = new VenueInfoBean(venue, "");
            model.addAttribute("venueInfoBean", venueInfoBean);
        }

        return "venues/newPlan";
    }

    @RequestMapping(value = "/modifyAddress.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage modifyAddress(@RequestBody AddressBean addressBean) {
        return venueService.modifyAddress(addressBean);
    }

    @RequestMapping(value = "/modifyPassword.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage modifyPassword(@RequestBody PasswordBean passwordBean) {
        return venueService.modifyPassword(passwordBean);
    }

    @RequestMapping(value = "/modifySeats.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage modifySeats(@RequestBody List<SeatInfoBean> seatInfoBeans) {
        return venueService.modifySeats(seatInfoBeans);
    }

    @RequestMapping(value = "/createNewPlan.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage createNewPlan(@RequestParam("eventName") String eventName, @RequestParam("startTime") String startTime,
                                @RequestParam("endTime") String endTime, @RequestParam("venueName") String venueName,
                                @RequestParam("location") String location, @RequestParam("type") String type,
                                @RequestParam("poster") MultipartFile file, @RequestParam("description") String description,
                                @RequestParam("notice") String notice, @RequestParam("prices") String prices) {
        Venue venue = venueService.getCurrentVenue();
        Map<String, Double> priceMap=new HashMap<>();

        // 检查输入内容
        try {
            Map convertMap = JSON.parseObject(prices, Map.class);

            for (Object obj : convertMap.keySet()) {
                priceMap.put((String) obj, Double.valueOf((String) convertMap.get(obj)));
            }
        } catch (Exception e) {
            return new ResultMessage(ResultMessage.ERROR, "请检查价格输入");
        }

        String date = startTime.split(" ")[0];
        if (!endTime.contains(date)) {
            return new ResultMessage(ResultMessage.ERROR, "开始时间和结束时间须在同一天");
        }

        Timestamp start = Timestamp.valueOf(startTime);
        Timestamp end = Timestamp.valueOf(endTime);

        if (start.after(end)) {
            return new ResultMessage(ResultMessage.ERROR, "开始时间须在结束时间之前");
        }

        String fileName = "";
        try {
            fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("/Users/keenan/Documents/workspace/Tickets/src/main/resources/static/img/" + fileName));
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ShowPlan showPlan = new ShowPlan(venue, eventName, "/img/" + fileName, start, end, ShowPlanType.fromString(type), ShowPlanStatus.fromString("有票"), notice, description);
        return showPlanService.createNewShowPlan(showPlan, priceMap);
    }


}
