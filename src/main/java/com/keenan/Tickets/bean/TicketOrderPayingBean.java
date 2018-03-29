package com.keenan.Tickets.bean;

import java.sql.Timestamp;

/**
 * @author keenan on 29/03/2018
 */
public class TicketOrderPayingBean {
    public Long orderId;

    public String orderNumber;

    public Double orderPrice;

    public Timestamp orderTime;

    public TicketOrderPayingBean() {
    }
}
