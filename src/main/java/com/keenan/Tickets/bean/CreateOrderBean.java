package com.keenan.Tickets.bean;

/**
 * @author keenan on 27/03/2018
 */
public class CreateOrderBean {
    public Long showPlanId;

    public Long venueId;

    public Double totalPrice;

    public String chosenSeats;

    public String email;

    public String section;

    public CreateOrderBean() {
    }

    @Override
    public String toString() {
        return "CreateOrderBean{" +
                "showPlanId=" + showPlanId +
                ", venueId=" + venueId +
                ", totalPrice=" + totalPrice +
                ", chosenSeats='" + chosenSeats + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
