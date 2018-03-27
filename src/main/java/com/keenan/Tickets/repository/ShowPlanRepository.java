package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.ShowPlan;
import com.keenan.Tickets.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author keenan on 22/03/2018
 */
public interface ShowPlanRepository extends JpaRepository<ShowPlan, Long> {
    List<ShowPlan> findAllByStartTimeAfter(Timestamp timestamp);

    List<ShowPlan> findShowPlansByVenueAndStartTimeAfterAndEndTimeAfter(Venue venue, Timestamp timestamp, Timestamp timestamp2);
}