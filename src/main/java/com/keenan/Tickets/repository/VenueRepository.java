package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author keenan on 23/03/2018
 */
public interface VenueRepository extends JpaRepository<Venue, Long> {
    Venue findFirstByVenueId(String venueId);
}
