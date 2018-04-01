package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.bean.*;
import com.keenan.Tickets.model.*;
import com.keenan.Tickets.model.util.*;
import com.keenan.Tickets.repository.*;
import com.keenan.Tickets.service.AdminService;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keenan on 30/03/2018
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private VenuePermissionRepository venuePermissionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private ShowPlanRepository showPlanRepository;
    @Autowired
    private TicketOrderRepository ticketOrderRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;


    @Override
    public AdminVenueApproveBean getAdminVenueApproveBean() {
        List<VenuePermission> permissions = venuePermissionRepository.findVenuePermissionsByStatus(PermissionStatus.WAIT_LIST);
        List<AdminVenueModifyApproveBean> modifyApproveBeans = new ArrayList<>();
        List<AdminVenueRegisterApproveBean> registerApproveBeans = new ArrayList<>();

        for (VenuePermission venuePermission : permissions) {
            if (venuePermission.getType().equals(PermissionType.VENUE_REGISTER)) {
                AdminVenueRegisterApproveBean registerApproveBean = new AdminVenueRegisterApproveBean();
                registerApproveBean.permissionId = venuePermission.getId();
                registerApproveBean.venueId = venuePermission.getVenue().getId();
                registerApproveBean.address = venuePermission.getVenue().getAddress();
                registerApproveBean.venueName = venuePermission.getVenue().getName();
                registerApproveBean.loginCode = venuePermission.getVenue().getVenueId();
                registerApproveBean.email = userRepository.findUserByUsername(venuePermission.getVenue().getVenueId()).getEmail();
                registerApproveBeans.add(registerApproveBean);
            } else if (venuePermission.getType().equals(PermissionType.VENUE_ADDRESS)) {
                AdminVenueModifyApproveBean modifyApproveBean = new AdminVenueModifyApproveBean();
                modifyApproveBean.permissionId = venuePermission.getId();
                modifyApproveBean.venueId = venuePermission.getVenue().getId();
                modifyApproveBean.venueName = venuePermission.getVenue().getName();
                modifyApproveBean.oldAddress = venuePermission.getVenue().getAddress();
                modifyApproveBean.newAddress = venuePermission.getModifyContent();
                modifyApproveBeans.add(modifyApproveBean);
            }
        }

        return new AdminVenueApproveBean(registerApproveBeans, modifyApproveBeans);
    }

    @Override
    public List<SettlementInfoBean> getSettlementInfos() {
        List<SettlementInfoBean> settlementInfoBeans = new ArrayList<>();
        List<ShowPlan> completedShowPlans = showPlanRepository.findShowPlansByShowPlanStatus(ShowPlanStatus.FINISHED);
        // 活动已完成
        for (ShowPlan showPlan : completedShowPlans) {
            // 活动未结算
            if (!showPlan.getSettled()) {
                SettlementInfoBean settlementInfoBean = new SettlementInfoBean();
                settlementInfoBean.showId = showPlan.getId();
                settlementInfoBean.showName = showPlan.getShowName();
                List<TicketOrder> ticketOrders = ticketOrderRepository.findTicketOrdersByShowPlanAndOrderStatus(showPlan, OrderStatus.COMPLETED);
                Double totalRevenue = 0.0;
                for (TicketOrder ticketOrder : ticketOrders) {
                    totalRevenue += ticketOrder.getOrderPrice();
                }
                settlementInfoBean.totalRevenue = totalRevenue;
                settlementInfoBeans.add(settlementInfoBean);
            }
        }
        return settlementInfoBeans;
    }

    @Override
    public ResultMessage processApprove(AdminPermissionPostBean adminPermissionPostBean) {
        VenuePermission venuePermission = venuePermissionRepository.findFirstById(adminPermissionPostBean.permissionId);

        if (venuePermission.getType().equals(PermissionType.VENUE_REGISTER)) {
            if (adminPermissionPostBean.isApprove) {
                venuePermission.setStatus(PermissionStatus.PASS);
                venuePermissionRepository.save(venuePermission);

                User user = userRepository.findUserByUsername(venuePermission.getVenue().getVenueId());
                user.setConfirmed(true);
                userRepository.save(user);

                return new ResultMessage(ResultMessage.SUCCESS, "已通过");
            } else {
                venuePermission.setStatus(PermissionStatus.DENY);
                venuePermissionRepository.save(venuePermission);
                return new ResultMessage(ResultMessage.SUCCESS, "已拒绝");
            }
        } else if (venuePermission.getType().equals(PermissionType.VENUE_ADDRESS)) {
            if (adminPermissionPostBean.isApprove) {
                Venue venue = venuePermission.getVenue();
                venue.setAddress(venuePermission.getModifyContent());
                venue.setEditPermit(true);
                venueRepository.save(venue);

                venuePermission.setStatus(PermissionStatus.PASS);
                venuePermissionRepository.save(venuePermission);
                return new ResultMessage(ResultMessage.SUCCESS, "已通过");
            } else {
                venuePermission.setStatus(PermissionStatus.DENY);
                venuePermissionRepository.save(venuePermission);
                return new ResultMessage(ResultMessage.SUCCESS, "已拒绝");
            }
        }
        return new ResultMessage(ResultMessage.ERROR, "发生了错误");
    }

    @Override
    public ResultMessage settleShowPlan(Long showPlanId) {
        ShowPlan showPlan = showPlanRepository.findFirstById(showPlanId);
        showPlan.setSettled(true);
        showPlanRepository.save(showPlan);
        return new ResultMessage(ResultMessage.SUCCESS, "已结算");
    }

    @Override
    public List<AdminUserBriefBean> getUserBriefs() {
        List<AdminUserBriefBean> adminUserBriefBeans = new ArrayList<>();
        SysRole role = sysRoleRepository.findSysRoleByName("ROLE_USER");
        List<User> users = userRepository.findUsersByRole(role);
        for (User user : users) {
            AdminUserBriefBean adminUserBriefBean = new AdminUserBriefBean();
            adminUserBriefBean.userId = user.getId();
            adminUserBriefBean.email = user.getEmail();
            adminUserBriefBean.username = user.getUsername();
            adminUserBriefBean.isValid = user.getValid();
            adminUserBriefBeans.add(adminUserBriefBean);
        }
        return adminUserBriefBeans;
    }

    @Override
    public ResultMessage blockUser(Long userId) {
        User user = userRepository.findFirstById(userId);
        user.setValid(false);
        userRepository.save(user);
        return new ResultMessage(ResultMessage.SUCCESS,"封号成功");
    }

    @Override
    public ResultMessage unBlockUser(Long userId) {
        User user=userRepository.findFirstById(userId);
        user.setValid(true);
        userRepository.save(user);
        return new ResultMessage(ResultMessage.SUCCESS,"解封成功");
    }
}
