package com.keenan.Tickets.bean;

import com.keenan.Tickets.model.ShowPlan;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author keenan on 22/03/2018
 */
public class ShowPlanDetailBean {
    public Long id;

    public String showName;

    public String posterUrl;

    public String startTime;

    public String endTime;

    public String venueAddress;

    public String showPlanType;

    public String showPlanStatus;

    public String notice;

    public String description;

    public ShowPlanDetailBean(ShowPlan showPlan) {
        this.id = showPlan.getId();
        this.showName = showPlan.getShowName();
        this.posterUrl = showPlan.getPosterUrl();

        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Timestamp start = showPlan.getStartTime();
        Timestamp end = showPlan.getEndTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        String weekDay = weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日_HH:mm");
        String startStr = df.format(start);
        String date = startStr.split("_")[0];
        String startTime = startStr.split("_")[1];
        String endTime = df.format(end).split("_")[1];

        this.startTime = date + " " + weekDay + " " + startTime;
        this.endTime = date + " " + weekDay + " " + endTime;

        this.venueAddress = showPlan.getVenue().getName() + " " + showPlan.getVenue().getAddress();
        this.showPlanType = showPlan.getShowPlanType().toString();
        this.showPlanStatus = showPlan.getShowPlanStatus().toString();

        this.notice = showPlan.getNotice();
        this.description = showPlan.getDescription();

    }
}
