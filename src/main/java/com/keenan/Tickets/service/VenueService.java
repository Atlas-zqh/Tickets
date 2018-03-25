package com.keenan.Tickets.service;

import com.keenan.Tickets.bean.AddressBean;
import com.keenan.Tickets.bean.PasswordBean;
import com.keenan.Tickets.bean.RegisterBean;
import com.keenan.Tickets.model.Venue;
import com.keenan.Tickets.util.ResultMessage;

/**
 * @author keenan on 23/03/2018
 */
public interface VenueService {
    ResultMessage signUpVenue(RegisterBean registerBean);

    Venue getCurrentVenue();

    String getVenueRegisterEmail(String loginCode);

    ResultMessage modifyAddress(AddressBean addressBean);

    ResultMessage modifyPassword(PasswordBean passwordBean);
}
