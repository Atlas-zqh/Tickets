package com.keenan.Tickets.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author keenan on 19/03/2018
 */
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Venue venue;

    private String seat_section;

    private Integer seat_row;

    private Integer seat_column;

    private Boolean isValid;

    @OneToMany(mappedBy = "seat", fetch = FetchType.EAGER)
    private List<SeatArrangement> seatArrangements;

    public Seat() {
    }

    public Seat(Venue venue, String seat_section, Integer seat_row, Integer seat_column, Boolean isValid) {
        this.venue = venue;
        this.seat_section = seat_section;
        this.seat_row = seat_row;
        this.seat_column = seat_column;
        this.isValid = isValid;
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

    public String getSeat_section() {
        return seat_section;
    }

    public void setSeat_section(String seat_section) {
        this.seat_section = seat_section;
    }

    public Integer getSeat_row() {
        return seat_row;
    }

    public void setSeat_row(Integer seat_row) {
        this.seat_row = seat_row;
    }

    public Integer getSeat_column() {
        return seat_column;
    }

    public void setSeat_column(Integer seat_column) {
        this.seat_column = seat_column;
    }

    public List<SeatArrangement> getSeatArrangements() {
        return seatArrangements;
    }

    public void setSeatArrangements(List<SeatArrangement> seatArrangements) {
        this.seatArrangements = seatArrangements;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
