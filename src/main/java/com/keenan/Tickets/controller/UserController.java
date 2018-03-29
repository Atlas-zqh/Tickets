package com.keenan.Tickets.controller;

import com.keenan.Tickets.bean.*;
import com.keenan.Tickets.model.*;
import com.keenan.Tickets.service.*;
import com.keenan.Tickets.util.ResultMessage;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keenan on 10/02/2018
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private VenueService venueService;
    @Autowired
    private ShowPlanService showPlanService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CouponService couponService;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String displaySignUp(Model model) {
        model.addAttribute("user", new RegisterBean());
        model.addAttribute("result", new ResultMessage(ResultMessage.SUCCESS));
        return "signUp";
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String displayConfirm(@RequestParam(value = "email", required = true) String email,
                                 @RequestParam(value = "code", required = true) String code,
                                 Model model) {
        ResultMessage resultMessage = userService.checkMail(email, code);
        if (resultMessage.getResultCode().equals(ResultMessage.ERROR)) {
            return "confirmFailure";
        } else {
            return "confirmSuccess";
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String displayUserInfo(Model model) throws Exception {
        User user = userService.getCurrentUser();
        UserInfoBean userInfoVO = new UserInfoBean(user);
        model.addAttribute("user", userInfoVO);

        List<ShowPlanBriefBean> showPlanBriefBeans = showPlanService.getAllShowPlanBriefBeansAfterTodayByUser(user);
        model.addAttribute("showPlanBriefBeans", showPlanBriefBeans);
        return "user/userinfo";
    }

    @RequestMapping(value = "/ordering", method = RequestMethod.GET)
    public String displayOrdering(@RequestParam(value = "showId", required = true) String showId, Model model) {
        User user = userService.getCurrentUser();
        // 添加演出信息
        ShowPlanBriefBean showPlanBriefBean = showPlanService.getBriefShowPlanByID(Long.valueOf(showId));
        model.addAttribute("curShowBrief", showPlanBriefBean);

        // 获得活动座位表
        ShowPlan showPlan = showPlanService.getShowPlanByID(Long.valueOf(showId));
        ChooseSeatBean chooseSeatBean = showPlanService.getSeatChart(showPlan);
        model.addAttribute("chooseSeatBean", chooseSeatBean);

        // 获得用户可用的优惠券
        List<CouponBriefInfoBean> couponBriefInfoBeans = new ArrayList<>();
        List<UserCoupon> userCoupons = user.getCoupons();
        LevelCoupon levelCoupon = user.getLevelCoupon();
        // 加入等级优惠(id=0)
        CouponBriefInfoBean levelBriefCoupon = new CouponBriefInfoBean(0L, levelCoupon.getCouponName(), levelCoupon.getDiscount());
        couponBriefInfoBeans.add(levelBriefCoupon);
        // 加入会员拥有的优惠券
        Timestamp today = new Timestamp(System.currentTimeMillis());
        userCoupons.forEach(userCoupon -> {
            if (userCoupon.getCoupon().getExpireTime().after(today) && !userCoupon.getUsed()) {
                couponBriefInfoBeans.add(new CouponBriefInfoBean(userCoupon.getId(), userCoupon.getCoupon().getCouponName(), userCoupon.getCoupon().getDiscount()));
            }
        });

        model.addAttribute("couponBriefInfoBeans", couponBriefInfoBeans);

        return "user/ordering";
    }

    @RequestMapping(value = "/paying", method = RequestMethod.GET)
    public String displayPaying(@RequestParam("orderId") Long orderId, Model model) {
        TicketOrderPayingBean payingBean = orderService.getTicketOrderPaying(orderId);
        model.addAttribute("payingBean", payingBean);
        return "user/paying";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String displayOrders(Model model) {
        User user = userService.getCurrentUser();
        UserOrdersBean userOrdersBean = orderService.getUserOrders(user);
        model.addAttribute("userOrdersBean", userOrdersBean);
        return "user/orders";
    }

    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
    public String displayOrderDetail(@RequestParam("orderId") Long orderId, Model model) {
        UserOrderDetailBean detailBean = orderService.getUserOrderDetail(orderId);
        model.addAttribute("detailBean", detailBean);
        return "user/orderDetail";
    }

    @RequestMapping(value = "/coupons", method = RequestMethod.GET)
    public String displayCoupons(Model model) {
        User user = userService.getCurrentUser();
        List<CouponBriefInfoBean> couponBriefInfoBeans = new ArrayList<>();
        List<UserCoupon> userCoupons = user.getCoupons();
        LevelCoupon levelCoupon = user.getLevelCoupon();
        // 加入登记优惠
        CouponBriefInfoBean levelBriefBean = new CouponBriefInfoBean(0L, levelCoupon.getCouponName(), levelCoupon.getDiscount(), "永久有效", 0.0);
        couponBriefInfoBeans.add(levelBriefBean);
        Timestamp today = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        userCoupons.forEach(userCoupon -> {
            if (userCoupon.getCoupon().getExpireTime().after(today) && !userCoupon.getUsed()) {
                couponBriefInfoBeans.add(new CouponBriefInfoBean(userCoupon.getId(), userCoupon.getCoupon().getCouponName(), userCoupon.getCoupon().getDiscount(), dateFormat.format(userCoupon.getCoupon().getExpireTime()), userCoupon.getCoupon().getNeedPoints()));
            }
        });

        model.addAttribute("myCoupons", couponBriefInfoBeans);

        List<CouponBriefInfoBean> couponMarket = new ArrayList<>();
        List<Coupon> coupons = couponService.getAllCouponsNotExpired();
        coupons.forEach(coupon -> {
            couponMarket.add(new CouponBriefInfoBean(coupon.getId(), coupon.getCouponName(), coupon.getDiscount(), dateFormat.format(coupon.getExpireTime()), coupon.getNeedPoints()));
        });

        model.addAttribute("couponMarket", couponMarket);
        model.addAttribute("remainPoints", user.getPoints());
        return "user/coupons";
    }

    /**
     * POST
     */

    @RequestMapping(value = "/register.action", method = RequestMethod.POST)
    public String signUp(@ModelAttribute("user") RegisterBean registerBean, BindingResult bindingResult, Model model) throws Exception {
        System.out.println("registerBean = " + registerBean.toString());
        if (registerBean.getUserType().equals("ROLE_USER")) {
            ResultMessage resultMessage = userService.signUp(registerBean);

            if (resultMessage.getResultCode().equals(ResultMessage.ERROR)) {
                model.addAttribute("result", resultMessage);
                model.addAttribute("user", registerBean);
                return "signUp";
            } else {
                model.addAttribute("result", resultMessage);
                model.addAttribute("user", new RegisterBean());
                model.addAttribute("info", "请前往注册邮箱进行认证，点击邮件中链接完成认证");
                return "login";
            }
        } else if (registerBean.getUserType().equals("ROLE_VENUE")) {
            ResultMessage resultMessage = venueService.signUpVenue(registerBean);

            if (resultMessage.getResultCode().equals(ResultMessage.ERROR)) {
                model.addAttribute("result", resultMessage);
                model.addAttribute("user", registerBean);
                return "signUp";
            } else {
                model.addAttribute("result", resultMessage);
                model.addAttribute("user", new RegisterBean());
                model.addAttribute("info", "场馆的登录码为" + resultMessage.getResultMessage() + ", 请记住");
                return "login";
            }
        } else {
            return "error/403";
        }
    }

    @RequestMapping(value = "/modifyPassword.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage modifyPassword(@RequestBody PasswordBean passwordBean) {
        return userService.modifyPassword(passwordBean);
    }

    @RequestMapping(value = "/createOrder.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage createOrder(@RequestBody UserCreateOrderBean userCreateOrderBean) {
        System.out.println(userCreateOrderBean.toString());
        User user = userService.getCurrentUser();
        return orderService.createOrder(user, userCreateOrderBean);
    }

    @RequestMapping(value = "/pay.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage payOrder(@RequestBody PayInfoBean payInfoBean) {
        User user = userService.getCurrentUser();
        return orderService.payOrder(user, payInfoBean);
    }

    @RequestMapping(value = "/refund.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage refund(@RequestBody String orderId) {
        return orderService.refundOrder(Long.valueOf(orderId.split("=")[1]));
    }

    @RequestMapping(value = "/cancelOrder.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage cancelOrder(@RequestBody String orderId) {
        return orderService.cancelOrder(Long.valueOf(orderId.split("=")[1]));
    }

    @RequestMapping(value = "/changeCoupon.action", method = RequestMethod.POST)
    public @ResponseBody
    ResultMessage changeCoupon(@RequestBody String couponId) {
        return couponService.changeCoupon(userService.getCurrentUser().getId(), Long.valueOf(couponId.split("=")[1]));
    }


}
