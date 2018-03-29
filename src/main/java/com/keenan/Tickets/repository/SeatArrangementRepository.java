package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.Seat;
import com.keenan.Tickets.model.SeatArrangement;
import com.keenan.Tickets.model.ShowPlan;
import com.keenan.Tickets.model.util.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author keenan on 27/03/2018
 */
public interface SeatArrangementRepository extends JpaRepository<SeatArrangement, Long> {
    List<SeatArrangement> findSeatArrangementsByShowPlan(ShowPlan showPlan);

    SeatArrangement findSeatArrangementBySeatAndShowPlan(Seat seat, ShowPlan showPlan);

    List<SeatArrangement> findSeatArrangementsByShowPlanAndAndSeatStatus(ShowPlan showPlan, SeatStatus seatStatus);
}
