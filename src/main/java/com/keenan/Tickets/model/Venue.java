package com.keenan.Tickets.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author keenan on 19/03/2018
 */
@Entity
@Table(name = "venue")
public class Venue {
    @Id
    @GeneratedValue
    private Long id;

    private String venueId;

    private String password;

    private String name;

    private String address;

    private Boolean editPermit;

    @OneToMany(mappedBy = "venue", fetch = FetchType.LAZY)
    private Set<VenuePermission> venuePermissions;

    @OneToMany(mappedBy = "venue", fetch = FetchType.LAZY)
    private List<Seat> seats;

    @OneToMany(mappedBy = "venue", fetch = FetchType.LAZY)
    private Set<ShowPlan> showPlans;

    public Venue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<VenuePermission> getVenuePermissions() {
        return venuePermissions;
    }

    public void setVenuePermissions(Set<VenuePermission> venuePermissions) {
        this.venuePermissions = venuePermissions;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Set<ShowPlan> getShowPlans() {
        return showPlans;
    }

    public void setShowPlans(Set<ShowPlan> showPlans) {
        this.showPlans = showPlans;
    }

    public Venue(String venueId, String password, String name) {
        this.venueId = venueId;
        this.password = password;
        this.name = name;
    }

    public Boolean getEditPermit() {
        return editPermit;
    }

    public void setEditPermit(Boolean editPermit) {
        this.editPermit = editPermit;
    }
}
