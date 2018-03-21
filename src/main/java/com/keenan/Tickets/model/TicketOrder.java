package com.keenan.Tickets.model;

import com.keenan.Tickets.model.util.OrderStatus;
import com.keenan.Tickets.model.util.TicketOrderType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author keenan on 19/03/2018
 */
@Entity
@Table(name = "ticket_order")
public class TicketOrder {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private ShowPlan showPlan;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private Timestamp orderTime;

    private Timestamp payTime;

    @Enumerated(EnumType.STRING)
    private TicketOrderType ticketOrderType;

    private Double orderPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "ticketOrder",fetch = FetchType.EAGER)
    private Set<SeatArrangement> seatArrangements;

    public TicketOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShowPlan getShowPlan() {
        return showPlan;
    }

    public void setShowPlan(ShowPlan showPlan) {
        this.showPlan = showPlan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public TicketOrderType getTicketOrderType() {
        return ticketOrderType;
    }

    public void setTicketOrderType(TicketOrderType ticketOrderType) {
        this.ticketOrderType = ticketOrderType;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Set<SeatArrangement> getSeatArrangements() {
        return seatArrangements;
    }

    public void setSeatArrangements(Set<SeatArrangement> seatArrangements) {
        this.seatArrangements = seatArrangements;
    }
}