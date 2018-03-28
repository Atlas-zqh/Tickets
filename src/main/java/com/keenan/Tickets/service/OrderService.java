package com.keenan.Tickets.service;

import com.keenan.Tickets.util.ResultMessage;

/**
 * @author keenan on 28/03/2018
 */
public interface OrderService {
    ResultMessage checkTicket(String ticketNumber);
}
