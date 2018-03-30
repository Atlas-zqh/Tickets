package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.bean.AdminStatisticsBean;
import com.keenan.Tickets.bean.LineChartItemBean;
import com.keenan.Tickets.bean.PieChartItemBean;
import com.keenan.Tickets.model.*;
import com.keenan.Tickets.model.util.OrderStatus;
import com.keenan.Tickets.model.util.ShowPlanStatus;
import com.keenan.Tickets.model.util.ShowPlanType;
import com.keenan.Tickets.repository.*;
import com.keenan.Tickets.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author keenan on 30/03/2018
 */
@Service
public class ChartServiceImpl implements ChartService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketOrderRepository ticketOrderRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private ShowPlanRepository showPlanRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public List<PieChartItemBean> getOrderPercentagePie(Long userId) {
        User user = userRepository.findFirstById(userId);
        List<TicketOrder> ticketOrders = ticketOrderRepository.findTicketOrdersByUser(user);
        Map<OrderStatus, Long> stat = ticketOrders.stream().collect(Collectors.groupingBy(TicketOrder::getOrderStatus, Collectors.counting()));
        List<PieChartItemBean> pieChartItemBeans = new ArrayList<>();

        for (Map.Entry<OrderStatus, Long> entry : stat.entrySet()) {
            PieChartItemBean itemBean = new PieChartItemBean();
            switch (entry.getKey()) {
                case INVALID_CANCELED:
                    itemBean.name = "已取消订单";
                    break;
                case INVALID_EXPIRED:
                    itemBean.name = "已过期订单";
                    break;
                case FAILED_ALLOCATE:
                    itemBean.name = "分配失败订单";
                    break;
                case INVALID_REFUND:
                    itemBean.name = "已退款订单";
                    break;
                case SUCCESS_UNPAID:
                    itemBean.name = "未支付订单";
                    break;
                case COMPLETED:
                    itemBean.name = "已结束订单";
                    break;
                case SUCCESS_PAID:
                    itemBean.name = "已支付订单";
                    break;
                default:
                    itemBean.name = "";
            }

            itemBean.value = entry.getValue().intValue();
            pieChartItemBeans.add(itemBean);
        }
        return pieChartItemBeans;

    }

    @Override
    public List<LineChartItemBean> getUserPayLineChart(Long userId) {
        User user = userRepository.findFirstById(userId);
        List<TicketOrder> ticketOrders = ticketOrderRepository.findTicketOrdersByUser(user);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Double> dateMoneyMap = new TreeMap<>();
        for (TicketOrder ticketOrder : ticketOrders) {
            if (ticketOrder.getOrderStatus().equals(OrderStatus.SUCCESS_PAID) || ticketOrder.getOrderStatus().equals(OrderStatus.COMPLETED)) {
                String date = df.format(ticketOrder.getPayTime());
                if (dateMoneyMap.get(date) == null) {
                    dateMoneyMap.put(date, ticketOrder.getOrderPrice());
                } else {
                    Double p = dateMoneyMap.get(date);
                    dateMoneyMap.put(date, p + ticketOrder.getOrderPrice());
                }
            }
        }

        List<LineChartItemBean> lineChartItemBeans = new ArrayList<>();
        for (Map.Entry<String, Double> entry : dateMoneyMap.entrySet()) {
            lineChartItemBeans.add(new LineChartItemBean(entry.getKey(), entry.getValue()));
        }
        return lineChartItemBeans;
    }

    @Override
    public List<LineChartItemBean> getVenueOrdersLineChart(Long venueId) {
        Venue venue = venueRepository.findFirstById(venueId);
        List<ShowPlan> showPlans = showPlanRepository.findShowPlansByVenue(venue);
        List<TicketOrder> ticketOrders = new ArrayList<>();
        for (ShowPlan showPlan : showPlans) {
            ticketOrders.addAll(ticketOrderRepository.findTicketOrdersByShowPlanAndOrderStatus(showPlan, OrderStatus.COMPLETED));
            ticketOrders.addAll(ticketOrderRepository.findTicketOrdersByShowPlanAndOrderStatus(showPlan, OrderStatus.SUCCESS_PAID));
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Double> dateMoneyMap = new TreeMap<>();
        for (TicketOrder ticketOrder : ticketOrders) {
            if (ticketOrder.getOrderStatus().equals(OrderStatus.SUCCESS_PAID) || ticketOrder.getOrderStatus().equals(OrderStatus.COMPLETED)) {
                String date = df.format(ticketOrder.getOrderTime());
                if (dateMoneyMap.get(date) == null) {
                    dateMoneyMap.put(date, 1.0);
                } else {
                    dateMoneyMap.put(date, 1.0 + dateMoneyMap.get(date));
                }
            }
        }

        List<LineChartItemBean> lineChartItemBeans = new ArrayList<>();
        for (Map.Entry<String, Double> entry : dateMoneyMap.entrySet()) {
            lineChartItemBeans.add(new LineChartItemBean(entry.getKey(), entry.getValue()));
        }
        return lineChartItemBeans;
    }

    @Override
    public List<LineChartItemBean> getVenueRevenue(Long venueId) {
        Venue venue = venueRepository.findFirstById(venueId);
        List<ShowPlan> showPlans = showPlanRepository.findShowPlansByVenue(venue);
        List<TicketOrder> ticketOrders = new ArrayList<>();
        for (ShowPlan showPlan : showPlans) {
            ticketOrders.addAll(ticketOrderRepository.findTicketOrdersByShowPlanAndOrderStatus(showPlan, OrderStatus.COMPLETED));
            ticketOrders.addAll(ticketOrderRepository.findTicketOrdersByShowPlanAndOrderStatus(showPlan, OrderStatus.SUCCESS_PAID));
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Double> dateMoneyMap = new TreeMap<>();
        for (TicketOrder ticketOrder : ticketOrders) {
            if (ticketOrder.getOrderStatus().equals(OrderStatus.SUCCESS_PAID) || ticketOrder.getOrderStatus().equals(OrderStatus.COMPLETED)) {
                String date = df.format(ticketOrder.getOrderTime());
                if (dateMoneyMap.get(date) == null) {
                    dateMoneyMap.put(date, ticketOrder.getOrderPrice());
                } else {
                    Double p = dateMoneyMap.get(date);
                    dateMoneyMap.put(date, p + ticketOrder.getOrderPrice());
                }
            }
        }

        List<LineChartItemBean> lineChartItemBeans = new ArrayList<>();
        for (Map.Entry<String, Double> entry : dateMoneyMap.entrySet()) {
            lineChartItemBeans.add(new LineChartItemBean(entry.getKey(), entry.getValue()));
        }
        return lineChartItemBeans;
    }

    /**
     * 场馆收益排名
     *
     * @return
     */
    @Override
    public List<LineChartItemBean> getVenueRevenueRank() {
        List<LineChartItemBean> lineChartItemBeans = new ArrayList<>();
        List<Venue> venues = venueRepository.findAll();
        for (Venue venue : venues) {
            List<ShowPlan> showPlans = showPlanRepository.findShowPlansByVenue(venue);
            if (showPlans == null || showPlans.size() == 0) {
                lineChartItemBeans.add(new LineChartItemBean(venue.getName(), 0.0));
                continue;
            }

            List<TicketOrder> ticketOrders = new ArrayList<>();
            for (ShowPlan showPlan : showPlans) {
                ticketOrders.addAll(ticketOrderRepository.findTicketOrdersByShowPlanAndOrderStatus(showPlan, OrderStatus.COMPLETED));
                ticketOrders.addAll(ticketOrderRepository.findTicketOrdersByShowPlanAndOrderStatus(showPlan, OrderStatus.SUCCESS_PAID));
            }

            if (ticketOrders.size() == 0) {
                lineChartItemBeans.add(new LineChartItemBean(venue.getName(), 0.0));
                continue;
            }

            Double revenue = ticketOrders.stream().map(ticketOrder -> ticketOrder.getOrderPrice() * 0.8).reduce(0.0, Double::sum);
            lineChartItemBeans.add(new LineChartItemBean(venue.getName(), revenue));
        }

        lineChartItemBeans.sort((LineChartItemBean b1, LineChartItemBean b2) -> b2.yAxis.compareTo(b1.yAxis));
        return lineChartItemBeans;
    }

    /**
     * 演出类型饼图
     *
     * @return
     */
    @Override
    public List<PieChartItemBean> getShowPlanTypePie() {
        List<ShowPlan> showPlans = showPlanRepository.findAll();
        Map<ShowPlanType, Long> stat = showPlans.stream().collect(Collectors.groupingBy(ShowPlan::getShowPlanType, Collectors.counting()));

        List<PieChartItemBean> pieChartItemBeans = new ArrayList<>();
        for (Map.Entry<ShowPlanType, Long> entry : stat.entrySet()) {
            PieChartItemBean pieChartItemBean = new PieChartItemBean();
            pieChartItemBean.name = entry.getKey().toString();
            pieChartItemBean.value = entry.getValue().intValue();
            pieChartItemBeans.add(pieChartItemBean);
        }
        return pieChartItemBeans;
    }

    /**
     * 用户订单数排名（已付钱和已完成）
     *
     * @return
     */
    @Override
    public List<LineChartItemBean> getUserOrderNumRank() {
        List<TicketOrder> ticketOrders = new ArrayList<>();
        ticketOrders.addAll(ticketOrderRepository.findTicketOrdersByOrderStatus(OrderStatus.COMPLETED));
        ticketOrders.addAll(ticketOrderRepository.findTicketOrdersByOrderStatus(OrderStatus.SUCCESS_PAID));

        Function<TicketOrder, User> f1 = TicketOrder::getUser;
        Function<User, String> f2 = User::getUsername;

        Map<String, Long> userOrders = ticketOrders.stream().collect(Collectors.groupingBy(f1.andThen(f2), Collectors.counting()));
        List<LineChartItemBean> lineChartItemBeans = new ArrayList<>();

        userOrders.entrySet().stream().forEach(stringLongEntry -> {
            LineChartItemBean lineChartItemBean = new LineChartItemBean();
            lineChartItemBean.xAxis = stringLongEntry.getKey();
            lineChartItemBean.yAxis = stringLongEntry.getValue().doubleValue();
            lineChartItemBeans.add(lineChartItemBean);
        });

        lineChartItemBeans.sort((LineChartItemBean b1, LineChartItemBean b2) -> b2.yAxis.compareTo(b1.yAxis));
        return lineChartItemBeans;
    }

    /**
     * 订单情况饼图
     *
     * @return
     */
    @Override
    public List<PieChartItemBean> getTicketOrderTypePercentPie() {
        List<TicketOrder> ticketOrders = ticketOrderRepository.findAll();
        Map<OrderStatus, Long> stat = ticketOrders.stream().collect(Collectors.groupingBy(TicketOrder::getOrderStatus, Collectors.counting()));
        List<PieChartItemBean> pieChartItemBeans = new ArrayList<>();

        for (Map.Entry<OrderStatus, Long> entry : stat.entrySet()) {
            PieChartItemBean itemBean = new PieChartItemBean();
            switch (entry.getKey()) {
                case INVALID_CANCELED:
                    itemBean.name = "已取消订单";
                    break;
                case INVALID_EXPIRED:
                    itemBean.name = "已过期订单";
                    break;
                case FAILED_ALLOCATE:
                    itemBean.name = "分配失败订单";
                    break;
                case INVALID_REFUND:
                    itemBean.name = "已退款订单";
                    break;
                case SUCCESS_UNPAID:
                    itemBean.name = "未支付订单";
                    break;
                case COMPLETED:
                    itemBean.name = "已结束订单";
                    break;
                case SUCCESS_PAID:
                    itemBean.name = "已支付订单";
                    break;
                default:
                    itemBean.name = "";
            }

            itemBean.value = entry.getValue().intValue();
            pieChartItemBeans.add(itemBean);
        }
        return pieChartItemBeans;
    }

    @Override
    public List<AdminStatisticsBean> getStatisticsTable() {
        List<AdminStatisticsBean> stat = new ArrayList<>();
        SysRole sysRole = sysRoleRepository.findSysRoleByName("ROLE_USER");
        Number userCnt = userRepository.findUsersByRole(sysRole).size();
        stat.add(new AdminStatisticsBean("用户总数", userCnt));

        Number ordersCnt = ticketOrderRepository.count();
        stat.add(new AdminStatisticsBean("订单总数", ordersCnt));

        Number completeOrdersCnt = ticketOrderRepository.findTicketOrdersByOrderStatus(OrderStatus.COMPLETED).size();
        stat.add(new AdminStatisticsBean("已完成订单总数", completeOrdersCnt));

        Number showPlansCnt = showPlanRepository.count();
        stat.add(new AdminStatisticsBean("活动总数", showPlansCnt));

        Number venuesCnt = venueRepository.count();
        stat.add(new AdminStatisticsBean("合作场馆总数", venuesCnt));

        List<ShowPlan> showPlans = showPlanRepository.findShowPlansByShowPlanStatus(ShowPlanStatus.FINISHED);
        Double total = 0.0;
        for (ShowPlan showPlan : showPlans) {
            List<TicketOrder> ticketOrders = ticketOrderRepository.findTicketOrdersByShowPlanAndOrderStatus(showPlan, OrderStatus.COMPLETED);
            for (TicketOrder ticketOrder : ticketOrders) {
                total += (ticketOrder.getOrderPrice() * 0.2);
            }
        }

        stat.add(new AdminStatisticsBean("平台收益", total));

        return stat;
    }
}
