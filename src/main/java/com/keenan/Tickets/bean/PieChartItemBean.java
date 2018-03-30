package com.keenan.Tickets.bean;

/**
 * @author keenan on 30/03/2018
 */
public class PieChartItemBean {
    public Integer value;

    public String name;

    public PieChartItemBean() {
    }

    public PieChartItemBean(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
