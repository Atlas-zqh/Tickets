package com.keenan.Tickets.model;

import com.keenan.Tickets.model.util.PermissionStatus;
import com.keenan.Tickets.model.util.PermissionType;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author keenan on 19/03/2018
 */
@Entity
@Table(name = "venue_permission")
public class VenuePermission {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Venue venue;

    private Timestamp createTime;

    @Enumerated(EnumType.STRING)
    private PermissionStatus status;

    @Enumerated(EnumType.STRING)
    private PermissionType type;

    public VenuePermission() {
    }

    public VenuePermission(Venue venue, Timestamp createTime, PermissionStatus status, PermissionType type) {
        this.venue = venue;
        this.createTime = createTime;
        this.status = status;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public PermissionStatus getStatus() {
        return status;
    }

    public void setStatus(PermissionStatus status) {
        this.status = status;
    }

    public PermissionType getType() {
        return type;
    }

    public void setType(PermissionType type) {
        this.type = type;
    }
}
