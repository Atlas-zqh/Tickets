package com.keenan.Tickets.bean;

import com.keenan.Tickets.model.SeatArrangement;
import com.keenan.Tickets.model.TicketOrder;
import com.keenan.Tickets.model.util.OrderStatus;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author keenan on 29/03/2018
 */
public class UserOrderDetailBean {
    public Long orderId;

    public Long showPlanId;

    public String ticketNumber;

    public String orderType;

    public String userEmail;

    public String createOrderTime;

    public String showName;

    public String payTime;

    public Double orderPrice;

    public String orderStatus;

    public String startTime;

    public String endTime;

    public String address;

    public String showPlanType;

    public String seats;

    public Boolean showPay;

    public Boolean showReturn;

    public Boolean showCancel;

    public UserOrderDetailBean(TicketOrder ticketOrder) {
        this.orderId = ticketOrder.getId();
        this.showPlanId = ticketOrder.getShowPlan().getId();
        this.ticketNumber = ticketOrder.getTicketNumber();
        this.orderType = ticketOrder.getTicketOrderType().toString();
        this.userEmail = ticketOrder.getUser().getEmail();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createOrderTime = df.format(ticketOrder.getOrderTime());
        this.showName = ticketOrder.getShowPlan().getShowName();
        this.payTime = ticketOrder.getPayTime() == null ? "未支付" : df.format(ticketOrder.getPayTime());
        this.orderPrice = ticketOrder.getOrderPrice();
        this.orderStatus = ticketOrder.getOrderStatus().toString();

        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Timestamp start = ticketOrder.getShowPlan().getStartTime();
        Timestamp end = ticketOrder.getShowPlan().getEndTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        String weekDay = weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        SimpleDateFormat df2 = new SimpleDateFormat("MM月dd日_HH:mm");
        String startStr = df2.format(start);
        String date = startStr.split("_")[0];
        String startTime = startStr.split("_")[1];
        String endTime = df2.format(end).split("_")[1];

        this.startTime = date + " " + weekDay + " " + startTime;
        this.endTime = date + " " + weekDay + " " + endTime;
        this.address = ticketOrder.getShowPlan().getVenue().getName() + " " + ticketOrder.getShowPlan().getVenue().getAddress();
        this.showPlanType = ticketOrder.getShowPlan().getShowPlanType().toString();
        this.seats = getSeatsStr(ticketOrder.getOrderStatus(), new ArrayList<>(ticketOrder.getSeatArrangements()));

        decideButton(ticketOrder.getOrderStatus());
    }

    private String getSeatsStr(OrderStatus orderStatus, List<SeatArrangement> seatArrangements) {
        String str = "";

        if (seatArrangements == null || seatArrangements.size() == 0) {
            if (orderStatus.equals(OrderStatus.SUCCESS_PAID)) {
                return "等待配票";
            }
            return "";
        }
        for (int i = 0; i < seatArrangements.size(); i++) {
            SeatArrangement arrangement = seatArrangements.get(i);
            if (i == 0) {
                str += (arrangement.getSeat().getSeatSection() + " ");
            }

            str += (arrangement.getSeat().getSeatRow() + "排" + arrangement.getSeat().getSeatColumn() + "座；");
        }

        return str;
    }

    private void decideButton(OrderStatus orderStatus) {
        if (orderStatus.equals(OrderStatus.SUCCESS_UNPAID)) {
            this.showPay = true;
            this.showCancel = true;
        } else {
            this.showPay = false;
            this.showCancel = false;
        }

        if (orderStatus.equals(OrderStatus.SUCCESS_PAID)) {
            this.showReturn = true;
        } else {
            this.showReturn = false;
        }
    }


}
