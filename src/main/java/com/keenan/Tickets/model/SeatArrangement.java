package com.keenan.Tickets.model;

import com.keenan.Tickets.model.util.SeatStatus;

import javax.persistence.*;

/**
 * @author keenan on 19/03/2018
 */
@Entity
@Table(name = "seat_arrangement")
public class SeatArrangement {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Seat seat;

    @ManyToOne(fetch = FetchType.EAGER)
    private ShowPlan showPlan;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    private Double seatPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private TicketOrder ticketOrder;

    private String ticketNumber;

    public SeatArrangement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public ShowPlan getShowPlan() {
        return showPlan;
    }

    public void setShowPlan(ShowPlan showPlan) {
        this.showPlan = showPlan;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public Double getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(Double seatPrice) {
        this.seatPrice = seatPrice;
    }

    public TicketOrder getTicketOrder() {
        return ticketOrder;
    }

    public void setTicketOrder(TicketOrder ticketOrder) {
        this.ticketOrder = ticketOrder;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
