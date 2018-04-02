package com.keenan.Tickets.service;

import com.keenan.Tickets.bean.*;
import com.keenan.Tickets.model.User;
import com.keenan.Tickets.util.ResultMessage;

/**
 * @author keenan on 28/03/2018
 */
public interface OrderService {
    ResultMessage checkTicket(String ticketNumber, Long venueId);

    ResultMessage createOrder(User user, UserCreateOrderBean createOrderBean);

    TicketOrderPayingBean getTicketOrderPaying(Long orderId);

    ResultMessage payOrder(User user, PayInfoBean payInfoBean);

    UserOrdersBean getUserOrders(User user);

    UserOrderDetailBean getUserOrderDetail(Long orderId);

    ResultMessage refundOrder(Long orderId);

    ResultMessage cancelOrder(Long orderId);
}
