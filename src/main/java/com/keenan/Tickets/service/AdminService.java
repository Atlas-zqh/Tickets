package com.keenan.Tickets.service;

import com.keenan.Tickets.bean.AdminPermissionPostBean;
import com.keenan.Tickets.bean.AdminUserBriefBean;
import com.keenan.Tickets.bean.AdminVenueApproveBean;
import com.keenan.Tickets.bean.SettlementInfoBean;
import com.keenan.Tickets.util.ResultMessage;

import java.util.List;

/**
 * @author keenan on 30/03/2018
 */
public interface AdminService {
    AdminVenueApproveBean getAdminVenueApproveBean();

    ResultMessage processApprove(AdminPermissionPostBean adminPermissionPostBean);

    List<SettlementInfoBean> getSettlementInfos();

    ResultMessage settleShowPlan(Long showPlanId);

    List<AdminUserBriefBean> getUserBriefs();

    ResultMessage blockUser(Long userId);

    ResultMessage unBlockUser(Long userId);
}
