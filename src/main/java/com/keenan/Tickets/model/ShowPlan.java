package com.keenan.Tickets.model;

import com.keenan.Tickets.model.util.ShowPlanStatus;
import com.keenan.Tickets.model.util.ShowPlanType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author keenan on 19/03/2018
 */
@Entity
@Table(name = "show_plan")
public class ShowPlan {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Venue venue;

    private String showName;

    private String posterUrl;

    private Timestamp startTime;

    private Timestamp endTime;

    @Enumerated(EnumType.STRING)
    private ShowPlanType showPlanType;

    @Enumerated(EnumType.STRING)
    private ShowPlanStatus showPlanStatus;

    @Lob
    @Column
    private String notice;

    @Lob
    @Column
    private String description;

    @OneToMany(mappedBy = "showPlan", fetch = FetchType.LAZY)
    private List<TicketOrder> ticketOrders;

    @OneToMany(mappedBy = "showPlan", fetch = FetchType.LAZY)
    private List<SeatArrangement> seatArrangements;

    public ShowPlan() {
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public ShowPlanType getShowPlanType() {
        return showPlanType;
    }

    public void setShowPlanType(ShowPlanType showPlanType) {
        this.showPlanType = showPlanType;
    }

    public ShowPlanStatus getShowPlanStatus() {
        return showPlanStatus;
    }

    public void setShowPlanStatus(ShowPlanStatus showPlanStatus) {
        this.showPlanStatus = showPlanStatus;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TicketOrder> getTicketOrders() {
        return ticketOrders;
    }

    public void setTicketOrders(List<TicketOrder> ticketOrders) {
        this.ticketOrders = ticketOrders;
    }

    public List<SeatArrangement> getSeatArrangements() {
        return seatArrangements;
    }

    public void setSeatArrangements(List<SeatArrangement> seatArrangements) {
        this.seatArrangements = seatArrangements;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
