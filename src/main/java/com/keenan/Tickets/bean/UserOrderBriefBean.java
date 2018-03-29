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
public class UserOrderBriefBean {
    public Long orderId;

    public Long showPlanId;

    public String ticketNumber;

    public String showName;

    public String posterUrl;

    public String startTime;

    public String address;

    public String seats;

    public String styleStr;

    public String orderStatus;

    public String invalidReason;

    public Boolean showSeats;

    public UserOrderBriefBean() {
    }

    public UserOrderBriefBean(TicketOrder ticketOrder) {
        this.orderId = ticketOrder.getId();
        this.ticketNumber = ticketOrder.getTicketNumber();
        this.posterUrl = ticketOrder.getShowPlan().getPosterUrl();
        this.showName = ticketOrder.getShowPlan().getShowName();
        this.showPlanId = ticketOrder.getShowPlan().getId();

        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Timestamp start = ticketOrder.getShowPlan().getStartTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        String weekDay = weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日_HH:mm");
        String startStr = df.format(start);
        String date = startStr.split("_")[0];
        String startTime = startStr.split("_")[1];
        this.startTime = date + " " + weekDay + " " + startTime;

        this.address = ticketOrder.getShowPlan().getVenue().getName() + " " + ticketOrder.getShowPlan().getVenue().getAddress();
        this.seats = getSeatsStr(ticketOrder.getOrderStatus(), new ArrayList<>(ticketOrder.getSeatArrangements()));
        this.styleStr = getStyleStr(ticketOrder.getOrderStatus());
        this.orderStatus = ticketOrder.getOrderStatus().toString();
        this.invalidReason = getInvalidReason(ticketOrder.getOrderStatus());
    }

    private String getSeatsStr(OrderStatus orderStatus, List<SeatArrangement> seatArrangements) {
        String str = "";

        if (seatArrangements == null || seatArrangements.size() == 0) {
            if (orderStatus.equals(OrderStatus.SUCCESS_PAID)) {
                this.showSeats = true;
                return "等待配票";
            }
            this.showSeats = false;
            return "";
        }
        for (int i = 0; i < seatArrangements.size(); i++) {
            this.showSeats = true;
            SeatArrangement arrangement = seatArrangements.get(i);
            if (i == 0) {
                str += (arrangement.getSeat().getSeatSection() + " ");
            }

            str += (arrangement.getSeat().getSeatRow() + "排" + arrangement.getSeat().getSeatColumn() + "座；");
        }

        return str;
    }

    private String getStyleStr(OrderStatus orderStatus) {
        switch (orderStatus) {
            case SUCCESS_PAID:
                return "panel-success";
            case COMPLETED:
                return "panel-default";
            case SUCCESS_UNPAID:
                return "panel-warning";
            case INVALID_EXPIRED:
            case FAILED_ALLOCATE:
            case INVALID_REFUND:
            case INVALID_CANCELED:
                return "panel-danger";
            default:
                return "panel-default";
        }
    }

    private String getInvalidReason(OrderStatus orderStatus) {
        switch (orderStatus) {
            case INVALID_REFUND:
                return "申请退款";
            case FAILED_ALLOCATE:
                return "配票失败";
            case INVALID_EXPIRED:
                return "15分钟内未付款";
            case INVALID_CANCELED:
                return "订单取消";
            default:
                return "";
        }
    }
}
