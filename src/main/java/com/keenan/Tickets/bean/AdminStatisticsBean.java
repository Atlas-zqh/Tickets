package com.keenan.Tickets.bean;

/**
 * @author keenan on 30/03/2018
 */
public class AdminStatisticsBean {
    public String name;

    public Number value;

    public AdminStatisticsBean(String name, Number value) {
        this.name = name;
        this.value = value;
    }

    public AdminStatisticsBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }
}
