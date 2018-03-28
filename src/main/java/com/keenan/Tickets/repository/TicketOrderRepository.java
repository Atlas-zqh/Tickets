package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.TicketOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author keenan on 27/03/2018
 */
public interface TicketOrderRepository extends JpaRepository<TicketOrder, Long> {
    TicketOrder findFirstByTicketNumber(String ticketNumber);
}
