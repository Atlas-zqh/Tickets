package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.ShowPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author keenan on 22/03/2018
 */
public interface ShowPlanRepository extends JpaRepository<ShowPlan, Long> {
    List<ShowPlan> findAllByStartTimeAfter(Timestamp timestamp);
}