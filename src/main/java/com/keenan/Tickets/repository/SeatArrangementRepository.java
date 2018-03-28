package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.Seat;
import com.keenan.Tickets.model.SeatArrangement;
import com.keenan.Tickets.model.ShowPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author keenan on 27/03/2018
 */
public interface SeatArrangementRepository extends JpaRepository<SeatArrangement, Long> {
    List<SeatArrangement> findSeatArrangementsByShowPlan(ShowPlan showPlan);

    SeatArrangement findSeatArrangementBySeatAndShowPlanAndSeatPrice(Seat seat, ShowPlan showPlan, Double seatPrice);
}
