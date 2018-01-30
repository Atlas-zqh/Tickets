package com.keenan.Tickets.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author keenan on 28/01/2018
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String email;

    private Boolean isValid;

    private Boolean isConfirmed;

    private Double points;

    public User() {
    }

    public User(Long id, String username, String email, Boolean isValid, Boolean isConfirmed, Double points) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isValid = isValid;
        this.isConfirmed = isConfirmed;
        this.points = points;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", isValid=" + isValid +
                ", isConfirmed=" + isConfirmed +
                ", points=" + points +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }


}
