package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.bean.ShowPlanBriefBean;
import com.keenan.Tickets.bean.ShowPlanDetailBean;
import com.keenan.Tickets.model.ShowPlan;
import com.keenan.Tickets.repository.ShowPlanRepository;
import com.keenan.Tickets.service.ShowPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author keenan on 22/03/2018
 */
@Service
public class ShowPlanServiceImpl implements ShowPlanService {
    @Autowired
    private ShowPlanRepository showPlanRepository;

    @Override
    public List<ShowPlanBriefBean> getAllShowPlanBriefBeansBeforeToday() {
        Timestamp today = new Timestamp(System.currentTimeMillis());
        List<ShowPlan> showPlans = showPlanRepository.findAllByStartTimeAfter(today);
        showPlans.sort((ShowPlan o1, ShowPlan o2) -> o1.getStartTime().compareTo(o2.getStartTime()));
        List<ShowPlanBriefBean> showPlanBriefBeans = new ArrayList<>();
        showPlans.forEach(showPlan -> showPlanBriefBeans.add(new ShowPlanBriefBean(showPlan)));

        return showPlanBriefBeans;
    }

    @Override
    public ShowPlanDetailBean getShowPlanByID(Long id) {
        ShowPlan showPlan = showPlanRepository.findOne(id);
        return new ShowPlanDetailBean(showPlan);
    }
}
