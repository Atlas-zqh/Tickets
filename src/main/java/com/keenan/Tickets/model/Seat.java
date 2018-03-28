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

    private String seatSection;

    private Integer seatRow;

    private Integer seatColumn;

    private Boolean isValid;

    @OneToMany(mappedBy = "seat", fetch = FetchType.EAGER)
    private List<SeatArrangement> seatArrangements;

    public Seat() {
    }

    public Seat(Venue venue, String seatSection, Integer seatRow, Integer seatColumn, Boolean isValid) {
        this.venue = venue;
        this.seatSection = seatSection;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
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

    public String getSeatSection() {
        return seatSection;
    }

    public void setSeatSection(String seatSection) {
        this.seatSection = seatSection;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }

    public Integer getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(Integer seatColumn) {
        this.seatColumn = seatColumn;
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
