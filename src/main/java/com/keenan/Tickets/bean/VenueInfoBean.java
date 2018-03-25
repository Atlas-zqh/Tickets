package com.keenan.Tickets.bean;

import com.keenan.Tickets.model.Venue;

/**
 * @author keenan on 23/03/2018
 */
public class VenueInfoBean {
    public Long id;

    public String venueName;

    public String registerEmail;

    public String loginCode;

    public String venueAddress;

    public Boolean editPermit;

    public VenueInfoBean(Venue venue, String registerEmail) {
        this.id = venue.getId();
        this.venueName = venue.getName();
        this.registerEmail = registerEmail;
        this.loginCode = venue.getVenueId();
        this.venueAddress = venue.getAddress();
        this.editPermit = venue.getEditPermit();
    }
}
