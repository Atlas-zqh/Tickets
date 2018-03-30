package com.keenan.Tickets.service;

import com.keenan.Tickets.bean.AdminStatisticsBean;
import com.keenan.Tickets.bean.LineChartItemBean;
import com.keenan.Tickets.bean.PieChartItemBean;

import java.util.List;

/**
 * @author keenan on 30/03/2018
 */
public interface ChartService {
    List<PieChartItemBean> getOrderPercentagePie(Long userId);

    List<LineChartItemBean> getUserPayLineChart(Long userId);

    List<LineChartItemBean> getVenueOrdersLineChart(Long venueId);

    List<LineChartItemBean> getVenueRevenue(Long venueId);

    /**
     * 场馆收益排名
     *
     * @return
     */
    List<LineChartItemBean> getVenueRevenueRank();

    /**
     * 演出类型饼图
     * @return
     */
    List<PieChartItemBean> getShowPlanTypePie();

    /**
     * 用户订单数排名（已付钱和已完成）
     * @return
     */
    List<LineChartItemBean> getUserOrderNumRank();

    /**
     * 订单情况饼图
     * @return
     */
    List<PieChartItemBean> getTicketOrderTypePercentPie();

    List<AdminStatisticsBean> getStatisticsTable();
}
