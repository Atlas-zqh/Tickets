package com.keenan.Tickets.controller;

import com.keenan.Tickets.bean.*;
import com.keenan.Tickets.service.AdminService;
import com.keenan.Tickets.service.ChartService;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author keenan on 30/03/2018
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ChartService chartService;

    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    public String displayApprove(Model model) {
        AdminVenueApproveBean approveBean = adminService.getAdminVenueApproveBean();
        model.addAttribute("approveBean", approveBean);
        return "manager/approveCenter";
    }

    @RequestMapping(value = "/revenue", method = RequestMethod.GET)
    public String displayRevenue(Model model) {
        List<SettlementInfoBean> settlementInfoBeans = adminService.getSettlementInfos();
        model.addAttribute("settlementInfos", settlementInfoBeans);
        return "manager/ticketRevenue";
    }

    @RequestMapping(value = "/userManage", method = RequestMethod.GET)
    public String displayUserManage(Model model) {
        List<AdminUserBriefBean> adminUserBriefBeans = adminService.getUserBriefs();
        model.addAttribute("userBriefs", adminUserBriefBeans);
        return "manager/userManagement";
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public String displayStatistics(Model model) {
        List<LineChartItemBean> venueRevenueRank = chartService.getVenueRevenueRank();
        List<String> venueRevenueRankXLegend = venueRevenueRank.stream().map(LineChartItemBean::getxAxis).collect(Collectors.toList());
        List<Double> venueRevenueRankYLegend = venueRevenueRank.stream().map(LineChartItemBean::getyAxis).collect(Collectors.toList());
        model.addAttribute("venueRevenueRankXLegend", venueRevenueRankXLegend);
        model.addAttribute("venueRevenueRankYLegend", venueRevenueRankYLegend);

        List<PieChartItemBean> showPlanTypePie = chartService.getShowPlanTypePie();
        model.addAttribute("showPlanTypePie", showPlanTypePie);

        List<LineChartItemBean> userOrderNumRank = chartService.getUserOrderNumRank();
        List<String> userOrderNumRankXLegend = userOrderNumRank.stream().map(LineChartItemBean::getxAxis).collect(Collectors.toList());
        List<Double> userOrderNumRankYLegend = userOrderNumRank.stream().map(LineChartItemBean::getyAxis).collect(Collectors.toList());
        model.addAttribute("userOrderNumRankXLegend", userOrderNumRankXLegend);
        model.addAttribute("userOrderNumRankYLegend", userOrderNumRankYLegend);

        List<PieChartItemBean> ticketOrderTypePercentPie = chartService.getTicketOrderTypePercentPie();
        model.addAttribute("ticketOrderTypePercentPie", ticketOrderTypePercentPie);

        model.addAttribute("statisticList", chartService.getStatisticsTable());

        return "manager/statistics";
    }

    /**
     * POST
     */
    @RequestMapping(value = "/approve.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage approve(@RequestBody AdminPermissionPostBean adminPermissionPostBean) {
        return adminService.processApprove(adminPermissionPostBean);
    }

    @RequestMapping(value = "/settle.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage settle(@RequestBody String showId) {
        return adminService.settleShowPlan(Long.valueOf(showId.split("=")[1]));
    }

    @RequestMapping(value = "/block.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage block(@RequestBody String userId) {
        return adminService.blockUser(Long.valueOf(userId.split("=")[1]));
    }

    @RequestMapping(value = "/unblock.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage unblock(@RequestBody String userId) {
        return adminService.unBlockUser(Long.valueOf(userId.split("=")[1]));
    }
}
