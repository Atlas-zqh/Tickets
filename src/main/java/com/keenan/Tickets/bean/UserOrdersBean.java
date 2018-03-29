package com.keenan.Tickets.bean;

import java.util.List;

/**
 * @author keenan on 29/03/2018
 */
public class UserOrdersBean {
    public List<UserOrderBriefBean> allOrders;

    public List<UserOrderBriefBean> unpaidOrders;

    public List<UserOrderBriefBean> paidOrders;

    public List<UserOrderBriefBean> invalidOrders;

    public List<UserOrderBriefBean> completedOrders;

    public UserOrdersBean() {
    }

    public UserOrdersBean(List<UserOrderBriefBean> allOrders, List<UserOrderBriefBean> unpaidOrders, List<UserOrderBriefBean> paidOrders, List<UserOrderBriefBean> invalidOrders, List<UserOrderBriefBean> completedOrders) {
        this.allOrders = allOrders;
        this.unpaidOrders = unpaidOrders;
        this.paidOrders = paidOrders;
        this.invalidOrders = invalidOrders;
        this.completedOrders = completedOrders;
    }
}
