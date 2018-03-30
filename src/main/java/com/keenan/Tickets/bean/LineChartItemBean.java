package com.keenan.Tickets.bean;

import java.sql.Timestamp;

/**
 * @author keenan on 30/03/2018
 */
public class LineChartItemBean {
    public String xAxis;

    public Double yAxis;

    public LineChartItemBean(String xAxis, Double yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public String getxAxis() {
        return xAxis;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public Double getyAxis() {
        return yAxis;
    }

    public void setyAxis(Double yAxis) {
        this.yAxis = yAxis;
    }

    public LineChartItemBean() {
    }

}
