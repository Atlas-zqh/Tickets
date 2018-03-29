package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.bean.*;
import com.keenan.Tickets.model.*;
import com.keenan.Tickets.model.util.OrderStatus;
import com.keenan.Tickets.model.util.SeatStatus;
import com.keenan.Tickets.model.util.TicketOrderType;
import com.keenan.Tickets.repository.*;
import com.keenan.Tickets.service.OrderService;
import com.keenan.Tickets.service.ShowPlanService;
import com.keenan.Tickets.util.ParseSeatString;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author keenan on 28/03/2018
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TicketOrderRepository ticketOrderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private ShowPlanRepository showPlanRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatArrangementRepository seatArrangementRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private ShowPlanService showPlanService;

    @Override
    public ResultMessage checkTicket(String ticketNumber) {
        if (ticketNumber == null || ticketNumber.equals("")) {
            return new ResultMessage(ResultMessage.ERROR, "请输入订单号");
        }
        TicketOrder ticketOrder = ticketOrderRepository.findFirstByTicketNumber(ticketNumber);
        if (ticketOrder == null) {
            return new ResultMessage(ResultMessage.ERROR, "未找到对应订单");
        }
        if (!ticketOrder.getTicketOrderType().equals(TicketOrderType.SPOT_TICKETS) && !ticketOrder.getOrderStatus().equals(OrderStatus.SUCCESS_PAID)) {
            return new ResultMessage(ResultMessage.ERROR, "该订单无效");
        } else if (ticketOrder.getOrderStatus().equals(OrderStatus.COMPLETED)) {
            return new ResultMessage(ResultMessage.ERROR, "该订单已完成，无法使用");
        } else {
            ticketOrder.setOrderStatus(OrderStatus.COMPLETED);
            ticketOrderRepository.save(ticketOrder);

            User user = ticketOrder.getUser();
            user.setPoints(user.getPoints() + ticketOrder.getOrderPrice());
            userRepository.save(user);

            return new ResultMessage(ResultMessage.SUCCESS, "验票成功");
        }
    }

    @Override
    public ResultMessage createOrder(User user, UserCreateOrderBean createOrderBean) {
        if (createOrderBean.showPlanId == null || createOrderBean.totalPrice == null
                || createOrderBean.totalPrice == 0 || createOrderBean.section == null
                || createOrderBean.section.trim().equals("")) {
            return new ResultMessage(ResultMessage.ERROR, "内容有误");
        }

        ShowPlan showPlan = showPlanRepository.findOne(createOrderBean.showPlanId);
        Venue venue = showPlan.getVenue();
        TicketOrderType ticketOrderType = TicketOrderType.fromString(createOrderBean.ticketOrderType);
        // 创建订单
        TicketOrder ticketOrder = new TicketOrder(showPlan, user, new Timestamp(System.currentTimeMillis()), null, ticketOrderType, createOrderBean.totalPrice, String.valueOf(System.currentTimeMillis()), createOrderBean.seatNum, OrderStatus.SUCCESS_UNPAID);
        ticketOrderRepository.save(ticketOrder);
        // 改变座位状态
        if (ticketOrderType.equals(TicketOrderType.CHOOSE_SEATS)) {
            List<BriefSeatBean> seatBeans = ParseSeatString.parseFormString(createOrderBean.chosenSeats);
            for (BriefSeatBean briefSeatBean : seatBeans) {
                Seat seat = seatRepository.findFirstByVenueAndSeatColumnAndSeatRowAndSeatSectionAndIsValid(venue, briefSeatBean.col, briefSeatBean.row, createOrderBean.section, true);
                SeatArrangement arrangement = seatArrangementRepository.findSeatArrangementBySeatAndShowPlan(seat, showPlan);

                if (arrangement == null || arrangement.getSeatStatus().equals(SeatStatus.BOOKED)) {
                    return new ResultMessage(ResultMessage.ERROR, "选座失败");
                } else {
                    arrangement.setTicketOrder(ticketOrder);
                    arrangement.setSeatStatus(SeatStatus.BOOKED);
                    seatArrangementRepository.save(arrangement);
                }
            }
        }

        // 检查活动座位是否还有剩余
        showPlanService.checkShowPlanStatus(createOrderBean.showPlanId);

        // 优惠券失效
        if (createOrderBean.userCouponId != null && createOrderBean.userCouponId != 0L) {
            UserCoupon userCoupon = userCouponRepository.findOne(createOrderBean.userCouponId);
            userCoupon.setUsed(true);
            userCouponRepository.save(userCoupon);
        }
        return new ResultMessage(ResultMessage.SUCCESS, String.valueOf(ticketOrder.getId()));
    }

    @Override
    public TicketOrderPayingBean getTicketOrderPaying(Long orderId) {
        TicketOrder ticketOrder = ticketOrderRepository.findOne(orderId);
        TicketOrderPayingBean payingBean = new TicketOrderPayingBean();
        payingBean.orderId = ticketOrder.getId();
        payingBean.orderNumber = ticketOrder.getTicketNumber();
        payingBean.orderPrice = ticketOrder.getOrderPrice();
        payingBean.orderTime = ticketOrder.getOrderTime();
        return payingBean;
    }

    @Override
    public ResultMessage payOrder(User user, PayInfoBean payInfoBean) {
        // pay
        if (user.getBankAccount().equals(payInfoBean.bankAccount) && user.getBankPassword().equals(payInfoBean.password)) {
            TicketOrder ticketOrder = ticketOrderRepository.findOne(payInfoBean.orderId);

            if (ticketOrder.getOrderStatus().equals(OrderStatus.INVALID_EXPIRED)) {
                return new ResultMessage(ResultMessage.ERROR, "该订单已失效");
            }
            if (user.getBalance() < ticketOrder.getOrderPrice()) {
                return new ResultMessage(ResultMessage.ERROR, "账户余额不足");
            }
            user.setBalance(user.getBalance() - ticketOrder.getOrderPrice());
            // 完成订单之后才为用户加积分
//            user.setPoints(user.getPoints() + ticketOrder.getOrderPrice());
            userRepository.save(user);

            ticketOrder.setOrderStatus(OrderStatus.SUCCESS_PAID);
            ticketOrder.setPayTime(new Timestamp(System.currentTimeMillis()));
            ticketOrderRepository.save(ticketOrder);
            return new ResultMessage(ResultMessage.SUCCESS, "支付成功");
        } else {
            return new ResultMessage(ResultMessage.ERROR, "银行账号或密码错误");
        }

    }

    @Override
    public UserOrdersBean getUserOrders(User user) {
        UserOrdersBean userOrdersBean = new UserOrdersBean();
        List<TicketOrder> ticketOrders = ticketOrderRepository.findTicketOrdersByUser(user);
        ticketOrders.sort((TicketOrder t1, TicketOrder t2) -> t2.getOrderTime().compareTo(t1.getOrderTime()));

        List<UserOrderBriefBean> allOrders = new ArrayList<>();
        ticketOrders.forEach(ticketOrder -> allOrders.add(new UserOrderBriefBean(ticketOrder)));
        userOrdersBean.allOrders = allOrders;

        Map<OrderStatus, List<TicketOrder>> orderStatusMap = ticketOrders.stream().collect(Collectors.groupingBy(TicketOrder::getOrderStatus, Collectors.toList()));

        List<UserOrderBriefBean> completedOrders = new ArrayList<>();
        if (orderStatusMap.get(OrderStatus.COMPLETED) != null) {
            List<TicketOrder> completedTicketOrders = orderStatusMap.get(OrderStatus.COMPLETED);
            completedTicketOrders.forEach(ticketOrder -> completedOrders.add(new UserOrderBriefBean(ticketOrder)));
        }
        userOrdersBean.completedOrders = completedOrders;

        List<UserOrderBriefBean> paidOrders = new ArrayList<>();
        if (orderStatusMap.get(OrderStatus.SUCCESS_PAID) != null) {
            List<TicketOrder> pairTicketOrders = orderStatusMap.get(OrderStatus.SUCCESS_PAID);
            pairTicketOrders.forEach(ticketOrder -> paidOrders.add(new UserOrderBriefBean(ticketOrder)));
        }
        userOrdersBean.paidOrders = paidOrders;

        List<UserOrderBriefBean> unpaidOrders = new ArrayList<>();
        if (orderStatusMap.get(OrderStatus.SUCCESS_UNPAID) != null) {
            List<TicketOrder> unpaidTicketOrders = orderStatusMap.get(OrderStatus.SUCCESS_UNPAID);
            unpaidTicketOrders.forEach(ticketOrder -> unpaidOrders.add(new UserOrderBriefBean(ticketOrder)));
        }
        userOrdersBean.unpaidOrders = unpaidOrders;

        List<UserOrderBriefBean> invalidOrders = new ArrayList<>();
        List<TicketOrder> invalidTicketOrders = new ArrayList<>();
        if (orderStatusMap.get(OrderStatus.INVALID_EXPIRED) != null) {
            invalidTicketOrders.addAll(orderStatusMap.get(OrderStatus.INVALID_EXPIRED));
        }
        if (orderStatusMap.get(OrderStatus.INVALID_REFUND) != null) {
            invalidTicketOrders.addAll(orderStatusMap.get(OrderStatus.INVALID_REFUND));
        }
        if (orderStatusMap.get(OrderStatus.FAILED_ALLOCATE) != null) {
            invalidTicketOrders.addAll(orderStatusMap.get(OrderStatus.FAILED_ALLOCATE));
        }
        invalidTicketOrders.sort((TicketOrder t1, TicketOrder t2) -> t2.getOrderTime().compareTo(t1.getOrderTime()));
        invalidTicketOrders.forEach(ticketOrder -> invalidOrders.add(new UserOrderBriefBean(ticketOrder)));
        userOrdersBean.invalidOrders = invalidOrders;

        return userOrdersBean;
    }

    @Override
    public UserOrderDetailBean getUserOrderDetail(Long orderId) {
        TicketOrder ticketOrder = ticketOrderRepository.findOne(orderId);
        return new UserOrderDetailBean(ticketOrder);
    }

    @Override
    public ResultMessage refundOrder(Long orderId) {
        try {
            // 改订单状态
            TicketOrder ticketOrder = ticketOrderRepository.findOne(orderId);
            ticketOrder.setOrderStatus(OrderStatus.INVALID_REFUND);
            ticketOrderRepository.save(ticketOrder);
            // 还位置
            Set<SeatArrangement> arrangements = ticketOrder.getSeatArrangements();
            for (SeatArrangement arrangement : arrangements) {
                arrangement.setTicketOrder(null);
                arrangement.setSeatStatus(SeatStatus.AVAILABLE);
                seatArrangementRepository.save(arrangement);
            }

            // 还钱
            User user = ticketOrder.getUser();
            user.setBalance(user.getBalance() + ticketOrder.getOrderPrice());
            return new ResultMessage(ResultMessage.SUCCESS, "退款成功");

        } catch (Exception e) {
            return new ResultMessage(ResultMessage.ERROR, "退款失败");
        }
    }

    @Override
    public ResultMessage cancelOrder(Long orderId) {
        try {
            // 改订单状态
            TicketOrder ticketOrder = ticketOrderRepository.findOne(orderId);
            ticketOrder.setOrderStatus(OrderStatus.INVALID_CANCELED);
            ticketOrderRepository.save(ticketOrder);
            // 还位置
            Set<SeatArrangement> arrangements = ticketOrder.getSeatArrangements();
            for (SeatArrangement arrangement : arrangements) {
                arrangement.setTicketOrder(null);
                arrangement.setSeatStatus(SeatStatus.AVAILABLE);
                seatArrangementRepository.save(arrangement);
            }

            return new ResultMessage(ResultMessage.SUCCESS, "取消成功");
        } catch (Exception e) {
            return new ResultMessage(ResultMessage.ERROR, "取消失败");
        }
    }
}
