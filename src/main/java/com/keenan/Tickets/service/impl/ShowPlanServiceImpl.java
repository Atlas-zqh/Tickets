package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.bean.*;
import com.keenan.Tickets.model.*;
import com.keenan.Tickets.model.util.OrderStatus;
import com.keenan.Tickets.model.util.SeatStatus;
import com.keenan.Tickets.model.util.TicketOrderType;
import com.keenan.Tickets.repository.*;
import com.keenan.Tickets.service.ShowPlanService;
import com.keenan.Tickets.util.ParseSeatString;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
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
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketOrderRepository ticketOrderRepository;

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
    public ShowPlanDetailBean getDetailShowPlanByID(Long id) {
        ShowPlan showPlan = showPlanRepository.findOne(id);
        return new ShowPlanDetailBean(showPlan);
    }

    @Override
    public ShowPlan getShowPlanByID(Long id) {
        return showPlanRepository.findOne(id);
    }

    @Override
    public ShowPlanBriefBean getBriefShowPlanByID(Long id) {
        ShowPlan showPlan = showPlanRepository.findOne(id);
        return new ShowPlanBriefBean(showPlan);
    }

    @Override
    public List<ShowPlanBriefBean> getAllShowPlansByVenueAfterToday(Venue venue) {
        Timestamp today = new Timestamp(System.currentTimeMillis());
        List<ShowPlan> showPlans = showPlanRepository.findShowPlansByVenueAndStartTimeAfterAndEndTimeAfter(venue, today, today);
        showPlans.sort(Comparator.comparing(ShowPlan::getStartTime));
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
                SeatArrangement seatArrangement = new SeatArrangement(seat, showPlan, SeatStatus.AVAILABLE, priceMap.get(seat.getSeatSection()));
                seatArrangements.add(seatArrangement);
            }

            seatArrangementRepository.save(seatArrangements);
        } catch (Exception e) {
            return new ResultMessage(ResultMessage.ERROR, "添加失败");
        }
        return new ResultMessage(ResultMessage.SUCCESS, "创建成功");
    }

    @Override
    public ChooseSeatBean getSeatChart(ShowPlan showPlan) {
        List<SeatArrangement> seatArrangements = seatArrangementRepository.findSeatArrangementsByShowPlan(showPlan);

        Map<Double, List<String>> sectionSeat = new TreeMap<>();
        // group by section/price
        Map<Double, List<SeatArrangement>> seatsBySection = seatArrangements.stream()
                .collect(Collectors.groupingBy(SeatArrangement::getSeatPrice));
        for (Map.Entry<Double, List<SeatArrangement>> entry : seatsBySection.entrySet()) {
            Function<SeatArrangement, Seat> f1 = SeatArrangement::getSeat;
            Function<Seat, Integer> f2 = Seat::getSeatRow;
            TreeMap<Integer, List<SeatArrangement>> seatsByRowInSection = entry.getValue()
                    .stream()
                    .collect(Collectors.groupingBy(f1.andThen(f2), TreeMap::new, Collectors.toList()));

            List<String> curSectionSeats = new ArrayList<>();
            for (Map.Entry<Integer, List<SeatArrangement>> rowEntry : seatsByRowInSection.entrySet()) {
                rowEntry.getValue().sort(Comparator.comparing(o -> o.getSeat().getSeatColumn()));

                StringBuilder curRow = new StringBuilder();
                for (SeatArrangement seatArrangement : rowEntry.getValue()) {
                    if (seatArrangement.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
                        curRow.append("a");
                    } else if (seatArrangement.getSeatStatus().equals(SeatStatus.BOOKED)) {
                        curRow.append("c");
                    }
                }
                curSectionSeats.add(curRow.toString());
            }

            sectionSeat.put(entry.getKey(), curSectionSeats);
        }

        ChooseSeatBean chooseSeatBean = new ChooseSeatBean();
        chooseSeatBean.sectionSeat = sectionSeat;

        return chooseSeatBean;
    }

    @Override
    public ResultMessage createOfflineOrder(CreateOrderBean createOrderBean) {
        System.out.println(createOrderBean.toString());

        if (createOrderBean.venueId == null || createOrderBean.totalPrice == null
                || createOrderBean.totalPrice == 0 || createOrderBean.chosenSeats == null
                || createOrderBean.chosenSeats.trim().equals("")) {
            return new ResultMessage(ResultMessage.ERROR, "内容有误");
        }

        Venue venue = venueRepository.findOne(createOrderBean.venueId);
        ShowPlan showPlan = showPlanRepository.findOne(createOrderBean.showPlanId);
        TicketOrder ticketOrder = null;
        if (createOrderBean.email != null && !createOrderBean.email.equals("")) {
            User user = userRepository.findUserByEmail(createOrderBean.email);
            // 为会员加积分
            user.setPoints(user.getPoints() + createOrderBean.totalPrice);
            userRepository.save(user);
            // 为会员创建订单
            ticketOrder = new TicketOrder(showPlan, user, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), TicketOrderType.SPOT_TICKETS, createOrderBean.totalPrice, String.valueOf(System.currentTimeMillis()), OrderStatus.COMPLETED);
            ticketOrderRepository.save(ticketOrder);
        }

        // 将座位状态设置为已定
        List<BriefSeatBean> seatBeans = ParseSeatString.parseFormString(createOrderBean.chosenSeats);
        for (BriefSeatBean briefSeatBean : seatBeans) {
            Seat seat = seatRepository.findFirstByVenueAndSeatColumnAndSeatRowAndIsValid(venue, briefSeatBean.col, briefSeatBean.row, true);
            SeatArrangement arrangement = seatArrangementRepository.findSeatArrangementBySeatAndShowPlanAndSeatPrice(seat, showPlan, createOrderBean.totalPrice / seatBeans.size());
            if (arrangement == null || arrangement.getSeatStatus().equals(SeatStatus.BOOKED)) {
                return new ResultMessage(ResultMessage.ERROR, "选座失败");
            } else {
                arrangement.setTicketOrder(ticketOrder);
                arrangement.setSeatStatus(SeatStatus.BOOKED);
                seatArrangementRepository.save(arrangement);
            }
        }


        return new ResultMessage(ResultMessage.SUCCESS, "选座成功");
    }
}
