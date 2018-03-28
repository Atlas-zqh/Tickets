package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.model.TicketOrder;
import com.keenan.Tickets.model.User;
import com.keenan.Tickets.model.util.OrderStatus;
import com.keenan.Tickets.model.util.TicketOrderType;
import com.keenan.Tickets.repository.TicketOrderRepository;
import com.keenan.Tickets.repository.UserRepository;
import com.keenan.Tickets.service.OrderService;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author keenan on 28/03/2018
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TicketOrderRepository ticketOrderRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResultMessage checkTicket(String ticketNumber) {
        if (ticketNumber == null || ticketNumber.equals("")) {
            return new ResultMessage(ResultMessage.ERROR, "请输入订单号");
        }
        TicketOrder ticketOrder = ticketOrderRepository.findFirstByTicketNumber(ticketNumber);
        if (ticketOrder == null) {
            return new ResultMessage(ResultMessage.ERROR, "未找到对应订单");
        }
        if (!ticketOrder.getTicketOrderType().equals(TicketOrderType.SPOT_TICKETS) && !ticketOrder.getOrderStatus().equals(OrderStatus.SUCCESS_PAID)) {
            return new ResultMessage(ResultMessage.ERROR, "该订单无效");
        } else if (ticketOrder.getOrderStatus().equals(OrderStatus.COMPLETED)) {
            return new ResultMessage(ResultMessage.ERROR, "该订单已完成，无法使用");
        } else {
            ticketOrder.setOrderStatus(OrderStatus.COMPLETED);
            ticketOrderRepository.save(ticketOrder);

            User user = ticketOrder.getUser();
            user.setPoints(user.getPoints() + ticketOrder.getOrderPrice());
            userRepository.save(user);

            return new ResultMessage(ResultMessage.SUCCESS, "验票成功");
        }
    }
}
