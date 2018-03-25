package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.VenuePermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author keenan on 23/03/2018
 */
public interface VenuePermissionRepository extends JpaRepository<VenuePermission, Long> {
}
