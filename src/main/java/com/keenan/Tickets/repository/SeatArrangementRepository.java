package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.SeatArrangement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author keenan on 27/03/2018
 */
public interface SeatArrangementRepository extends JpaRepository<SeatArrangement, Long> {
}
