package com.keenan.Tickets.repository;

import com.keenan.Tickets.model.VenuePermission;
import com.keenan.Tickets.model.util.PermissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author keenan on 23/03/2018
 */
public interface VenuePermissionRepository extends JpaRepository<VenuePermission, Long> {
    List<VenuePermission> findVenuePermissionsByStatus(PermissionStatus permissionStatus);

    VenuePermission findFirstById(Long id);
}
