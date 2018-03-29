package com.keenan.Tickets.bean;

/**
 * @author keenan on 29/03/2018
 */
public class UserCreateOrderBean {
    public Long showPlanId;

    public Double totalPrice;

    public String chosenSeats;

    public Integer seatNum;

    public String ticketOrderType;

    public Long userCouponId;

    public String section;

    public UserCreateOrderBean() {
    }

    @Override
    public String toString() {
        return "UserCreateOrderBean{" +
                "showPlanId=" + showPlanId +
                ", totalPrice=" + totalPrice +
                ", chosenSeats='" + chosenSeats + '\'' +
                ", seatNum=" + seatNum +
                ", ticketOrderType='" + ticketOrderType + '\'' +
                ", userCouponId=" + userCouponId +
                ", section='" + section + '\'' +
                '}';
    }
}
