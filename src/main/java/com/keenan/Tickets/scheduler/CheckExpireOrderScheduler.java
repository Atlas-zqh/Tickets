package com.keenan.Tickets.scheduler;

import com.keenan.Tickets.model.SeatArrangement;
import com.keenan.Tickets.model.ShowPlan;
import com.keenan.Tickets.model.TicketOrder;
import com.keenan.Tickets.model.util.OrderStatus;
import com.keenan.Tickets.model.util.SeatStatus;
import com.keenan.Tickets.model.util.ShowPlanStatus;
import com.keenan.Tickets.repository.SeatArrangementRepository;
import com.keenan.Tickets.repository.ShowPlanRepository;
import com.keenan.Tickets.repository.TicketOrderRepository;
import com.sun.tools.corba.se.idl.constExpr.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * @author keenan on 29/03/2018
 */
@Component
public class CheckExpireOrderScheduler {
    @Autowired
    private SeatArrangementRepository seatArrangementRepository;
    @Autowired
    private TicketOrderRepository ticketOrderRepository;
    @Autowired
    private ShowPlanRepository showPlanRepository;

    @Scheduled(fixedRate = 1000)
    public void checkExpireOrder() throws InterruptedException {
        // 位置空出来
        List<TicketOrder> ticketOrders = ticketOrderRepository.findTicketOrdersByOrderStatus(OrderStatus.SUCCESS_UNPAID);
        for (TicketOrder ticketOrder : ticketOrders) {
            Timestamp createTime = ticketOrder.getOrderTime();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if ((now.getTime() - createTime.getTime()) >= 900000L) {
                Set<SeatArrangement> seatArrangements = ticketOrder.getSeatArrangements();
                for (SeatArrangement seatArrangement : seatArrangements) {
                    seatArrangement.setSeatStatus(SeatStatus.AVAILABLE);
                    seatArrangement.setTicketOrder(null);
                    seatArrangementRepository.save(seatArrangement);
                }

                ticketOrder.setOrderStatus(OrderStatus.INVALID_EXPIRED);
                ticketOrderRepository.save(ticketOrder);

                ShowPlan showPlan = ticketOrder.getShowPlan();
                showPlan.setShowPlanStatus(ShowPlanStatus.ABUNDANCE);
                showPlanRepository.save(showPlan);
            }
        }
    }
}
