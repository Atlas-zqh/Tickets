package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.Seat;
import com.keenan.Tickets.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author keenan on 26/03/2018
 */
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByVenue(Venue venue);

    List<Seat> findAllByVenueAndIsValid(Venue venue, Boolean isValid);

    List<Seat> findSeatsByVenueAndSeatColumnAndSeatRowAndIsValid(Venue venue, Integer seat_column, Integer seat_row, Boolean isValid);

    Seat findFirstByVenueAndSeatColumnAndSeatRowAndSeatSectionAndIsValid(Venue venue, Integer seat_column, Integer seat_row, String seat_section, Boolean isValid);

}
