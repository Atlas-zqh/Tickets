package com.keenan.Tickets.scheduler;

import com.keenan.Tickets.model.ShowPlan;
import com.keenan.Tickets.model.util.ShowPlanStatus;
import com.keenan.Tickets.repository.ShowPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author keenan on 30/03/2018
 */
@Component
public class CheckShowPlanCompleteScheduler {
    @Autowired
    private ShowPlanRepository showPlanRepository;

    @Scheduled(cron = "0 0 3 * * ?")
    public void checkShowPlanComplete() {
        List<ShowPlan> showPlans = showPlanRepository.findAll();
        Timestamp today = new Timestamp(System.currentTimeMillis());
        for (ShowPlan showPlan : showPlans) {
            if (showPlan.getEndTime().before(today)) {
                showPlan.setShowPlanStatus(ShowPlanStatus.FINISHED);
                showPlanRepository.save(showPlan);
            }
        }
    }
}
