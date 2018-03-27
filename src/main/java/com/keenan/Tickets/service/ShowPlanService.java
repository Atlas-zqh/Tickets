package com.keenan.Tickets.service;

import com.keenan.Tickets.bean.ShowPlanBriefBean;
import com.keenan.Tickets.bean.ShowPlanDetailBean;
import com.keenan.Tickets.model.ShowPlan;
import com.keenan.Tickets.model.Venue;
import com.keenan.Tickets.util.ResultMessage;

import java.util.List;
import java.util.Map;

/**
 * @author keenan on 21/03/2018
 */
public interface ShowPlanService {
    List<ShowPlanBriefBean> getAllShowPlanBriefBeansBeforeToday();

    ShowPlanDetailBean getShowPlanByID(Long id);

    List<ShowPlanBriefBean> getAllShowPlansByVenueAfterToday(Venue venue);

    ResultMessage createNewShowPlan(ShowPlan showPlan,Map<String, Double> priceMap);
}
