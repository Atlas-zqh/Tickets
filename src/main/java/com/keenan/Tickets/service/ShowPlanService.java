package com.keenan.Tickets.service;

import com.keenan.Tickets.bean.ShowPlanBriefBean;
import com.keenan.Tickets.bean.ShowPlanDetailBean;
import com.keenan.Tickets.model.ShowPlan;

import java.util.List;

/**
 * @author keenan on 21/03/2018
 */
public interface ShowPlanService {
    List<ShowPlanBriefBean> getAllShowPlanBriefBeansBeforeToday();

    ShowPlanDetailBean getShowPlanByID(Long id);
}
