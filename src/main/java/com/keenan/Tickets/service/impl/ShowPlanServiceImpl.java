package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.bean.ShowPlanBriefBean;
import com.keenan.Tickets.bean.ShowPlanDetailBean;
import com.keenan.Tickets.model.Seat;
import com.keenan.Tickets.model.SeatArrangement;
import com.keenan.Tickets.model.ShowPlan;
import com.keenan.Tickets.model.Venue;
import com.keenan.Tickets.model.util.SeatStatus;
import com.keenan.Tickets.repository.SeatArrangementRepository;
import com.keenan.Tickets.repository.SeatRepository;
import com.keenan.Tickets.repository.ShowPlanRepository;
import com.keenan.Tickets.service.ShowPlanService;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author keenan on 22/03/2018
 */
@Service
public class ShowPlanServiceImpl implements ShowPlanService {
    @Autowired
    private ShowPlanRepository showPlanRepository;
    @Autowired
    private SeatArrangementRepository seatArrangementRepository;
    @Autowired
    private SeatRepository seatRepository;

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

    @Override
    public List<ShowPlanBriefBean> getAllShowPlansByVenueAfterToday(Venue venue) {
        Timestamp today = new Timestamp(System.currentTimeMillis());
        List<ShowPlan> showPlans = showPlanRepository.findShowPlansByVenueAndStartTimeAfterAndEndTimeAfter(venue, today, today);
        showPlans.sort((ShowPlan o1, ShowPlan o2) -> o1.getStartTime().compareTo(o2.getStartTime()));
        List<ShowPlanBriefBean> showPlanBriefBeans = new ArrayList<>();
        showPlans.stream().forEach(showPlan -> showPlanBriefBeans.add(new ShowPlanBriefBean(showPlan)));

        return showPlanBriefBeans;
    }

    @Override
    public ResultMessage createNewShowPlan(ShowPlan showPlan, Map<String, Double> priceMap) {
        try {
            showPlanRepository.save(showPlan);
            List<Seat> seats = seatRepository.findAllByVenueAndIsValid(showPlan.getVenue(), true);
            List<SeatArrangement> seatArrangements = new ArrayList<>();

            for (Seat seat : seats) {
                SeatArrangement seatArrangement = new SeatArrangement(seat, showPlan, SeatStatus.AVAILABLE, priceMap.get(seat.getSeat_section()));
                seatArrangements.add(seatArrangement);
            }

            seatArrangementRepository.save(seatArrangements);
        } catch (Exception e) {
            return new ResultMessage(ResultMessage.ERROR, "添加失败");
        }
        return new ResultMessage(ResultMessage.SUCCESS, "创建成功");
    }
}
