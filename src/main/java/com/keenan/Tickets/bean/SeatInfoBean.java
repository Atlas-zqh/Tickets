package com.keenan.Tickets.bean;

/**
 * @author keenan on 26/03/2018
 */
public class SeatInfoBean {
    public Long venueId;

    public String section;

    public Integer row;

    public Integer col;

    public SeatInfoBean() {
    }

    public SeatInfoBean(Long venueId, String section, Integer row, Integer col) {
        this.venueId = venueId;
        this.section = section;
        this.row = row;
        this.col = col;
    }
}
