package com.keenan.Tickets.bean;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @author keenan on 26/03/2018
 */
public class NewPlanBean {
    public Long venueId;

    public String startTime;

    public String endTime;

    public String showPlanType;

    public String posterUrl;

    public String description;

    public String notice;

    public Map<String, Integer> prices;

    public NewPlanBean() {
    }
}
