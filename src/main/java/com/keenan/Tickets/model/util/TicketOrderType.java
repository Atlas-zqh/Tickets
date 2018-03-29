package com.keenan.Tickets.model.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author keenan on 20/03/2018
 */
public enum TicketOrderType {
    CHOOSE_SEATS("选座购买"), IMMEDIATE("立即购买"), SPOT_TICKETS("现场购票");

    private String text;

    private static final Map<String, TicketOrderType> stringToEnum = new HashMap<>();

    static {
        for (TicketOrderType ticketOrderType : values()) {
            stringToEnum.put(ticketOrderType.toString(), ticketOrderType);
        }
    }

    public static TicketOrderType fromString(String ticketOrderTypeStr) {
        return stringToEnum.get(ticketOrderTypeStr);
    }

    TicketOrderType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return text;
    }
}
