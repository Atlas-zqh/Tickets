package com.keenan.Tickets.controller;

import com.keenan.Tickets.bean.AddressBean;
import com.keenan.Tickets.bean.PasswordBean;
import com.keenan.Tickets.bean.RegisterBean;
import com.keenan.Tickets.bean.VenueInfoBean;
import com.keenan.Tickets.model.Venue;
import com.keenan.Tickets.service.VenueService;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author keenan on 23/03/2018
 */
@Controller
@RequestMapping("/venue")
public class VenueController {
    @Autowired
    private VenueService venueService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String displayVenueInfo(Model model) {
        Venue venue = venueService.getCurrentVenue();
        if (venue == null) {
            return "500";
        } else {
            String email = venueService.getVenueRegisterEmail(venue.getVenueId());
            VenueInfoBean venueInfoBean = new VenueInfoBean(venue, email);
            System.out.println("venueInfoBean = " + (venueInfoBean.venueAddress == null));
            model.addAttribute("venueInfoBean", venueInfoBean);
        }
        return "venues/venueInfo";
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


}
