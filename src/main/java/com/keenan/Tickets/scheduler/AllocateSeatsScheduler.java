package com.keenan.Tickets.scheduler;

import com.keenan.Tickets.model.SeatArrangement;
import com.keenan.Tickets.model.ShowPlan;
import com.keenan.Tickets.model.TicketOrder;
import com.keenan.Tickets.model.util.OrderStatus;
import com.keenan.Tickets.model.util.SeatStatus;
import com.keenan.Tickets.model.util.TicketOrderType;
import com.keenan.Tickets.repository.SeatArrangementRepository;
import com.keenan.Tickets.repository.ShowPlanRepository;
import com.keenan.Tickets.repository.TicketOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author keenan on 29/03/2018
 */
@Component
public class AllocateSeatsScheduler {
    @Autowired
    private TicketOrderRepository ticketOrderRepository;
    @Autowired
    private ShowPlanRepository showPlanRepository;
    @Autowired
    private SeatArrangementRepository seatArrangementRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void allocateSeats() {
        // 获得需要分配座位的活动
        Timestamp today = new Timestamp(System.currentTimeMillis());
        Timestamp twoWeekLater = new Timestamp(System.currentTimeMillis() + (long) (14 * 24 * 60 * 60 * 1000));
        List<ShowPlan> showPlans = showPlanRepository.findAllByStartTimeAfter(today);

        for (ShowPlan showPlan : showPlans) {
            if (showPlan.getStartTime().before(twoWeekLater)) {
                List<SeatArrangement> seatArrangements = seatArrangementRepository.findSeatArrangementsByShowPlanAndSeatStatus(showPlan, SeatStatus.AVAILABLE);
                int availableCnt = seatArrangements.size();
                int allocateIndex = 0;

                List<TicketOrder> ticketOrders = ticketOrderRepository.findTicketOrdersByShowPlan(showPlan);

                for (TicketOrder ticketOrder : ticketOrders) {
                    if (ticketOrder.getTicketOrderType().equals(TicketOrderType.IMMEDIATE)) {
                        // 有余票
                        if (availableCnt >= ticketOrder.getSeatNum()) {
                            // 配票
                            for (int i = 0; i < ticketOrder.getSeatNum(); i++) {
                                SeatArrangement arrangement = seatArrangements.get(allocateIndex++);
                                arrangement.setSeatStatus(SeatStatus.BOOKED);
                                arrangement.setTicketOrder(ticketOrder);
                                seatArrangementRepository.save(arrangement);
                                // 减去剩余票
                                availableCnt -= 1;
                            }
                        } else {
                            ticketOrder.setOrderStatus(OrderStatus.FAILED_ALLOCATE);
                            ticketOrderRepository.save(ticketOrder);
                        }
                    }
                }
            }
        }
    }
}
