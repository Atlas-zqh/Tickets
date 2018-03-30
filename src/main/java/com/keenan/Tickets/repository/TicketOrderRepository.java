package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.ShowPlan;
import com.keenan.Tickets.model.TicketOrder;
import com.keenan.Tickets.model.User;
import com.keenan.Tickets.model.util.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author keenan on 27/03/2018
 */
public interface TicketOrderRepository extends JpaRepository<TicketOrder, Long> {
    TicketOrder findFirstById(Long id);

    TicketOrder findFirstByTicketNumber(String ticketNumber);

    List<TicketOrder> findTicketOrdersByUser(User user);

    List<TicketOrder> findTicketOrdersByOrderStatus(OrderStatus orderStatus);

    List<TicketOrder> findTicketOrdersByShowPlan(ShowPlan showPlan);

    List<TicketOrder> findTicketOrdersByShowPlanAndOrderStatus(ShowPlan showPlan, OrderStatus status);

}
