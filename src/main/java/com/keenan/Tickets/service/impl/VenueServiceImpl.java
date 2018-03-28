package com.keenan.Tickets.service.impl;

import com.keenan.Tickets.bean.AddressBean;
import com.keenan.Tickets.bean.PasswordBean;
import com.keenan.Tickets.bean.RegisterBean;
import com.keenan.Tickets.bean.SeatInfoBean;
import com.keenan.Tickets.model.*;
import com.keenan.Tickets.model.util.PermissionStatus;
import com.keenan.Tickets.model.util.PermissionType;
import com.keenan.Tickets.repository.*;
import com.keenan.Tickets.service.VenueService;
import com.keenan.Tickets.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author keenan on 23/03/2018
 */
@Service
public class VenueServiceImpl implements VenueService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private VenuePermissionRepository venuePermissionRepository;
    @Autowired
    private ShowPlanRepository showPlanRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public ResultMessage signUpVenue(RegisterBean registerBean) {
        if (userRepository.findUserByEmail(registerBean.getEmail()) != null) {
            return new ResultMessage(ResultMessage.ERROR, "该邮箱已被注册!");
        }

        if (userRepository.findUserByUsername(registerBean.getUsername()) != null) {
            return new ResultMessage(ResultMessage.ERROR, "该场馆名已被注册!");
        }

        if (!registerBean.getEmail().matches("^\\w+@(\\w+\\.)+\\w+$")) {
            return new ResultMessage(ResultMessage.ERROR, "邮箱格式不合法!");
        }

        if (registerBean.getPassword().equals("") || registerBean.getConfirmPassword().equals("")) {
            return new ResultMessage(ResultMessage.ERROR, "请输入密码!");
        }

        if (!registerBean.getPassword().equals(registerBean.getConfirmPassword())) {
            return new ResultMessage(ResultMessage.ERROR, "两次输入的密码不一致!");
        }

        String loginCode = "";
        int venueNo = venueRepository.findAll().size() + 1;
        if (venueNo <= 9) {
            loginCode = "000000" + venueNo;
        } else if (venueNo <= 99) {
            loginCode = "00000" + venueNo;
        } else if (venueNo <= 999) {
            loginCode = "0000" + venueNo;
        } else if (venueNo <= 9999) {
            loginCode = "000" + venueNo;
        } else if (venueNo <= 99999) {
            loginCode = "00" + venueNo;
        } else if (venueNo <= 999999) {
            loginCode = "0" + venueNo;
        } else if (venueNo <= 9999999) {
            loginCode = "" + venueNo;
        } else {
            return new ResultMessage(ResultMessage.ERROR, "场馆数量到达上限，无法注册!");
        }

        SysRole role = sysRoleRepository.findSysRoleByName(registerBean.getUserType());
        // 以场馆登录码为用户名，在user表中加入场馆的信息，供登录使用
        // user中confirm为false，未通过管理员审核注册就无法登录
        User user = new User(loginCode, registerBean.getPassword(), registerBean.getEmail(), true, false, 0.0, 1, 0.0, "", role);
        userRepository.save(user);
        // 在场馆表中加入
        Venue venue = new Venue(loginCode, registerBean.getPassword(), registerBean.getUsername());
        venueRepository.save(venue);
        // 加入审核表
        VenuePermission venuePermission = new VenuePermission(venue, new Timestamp(System.currentTimeMillis()), PermissionStatus.WAIT_LIST, PermissionType.VENUE_REGISTER);
        venuePermissionRepository.save(venuePermission);
        return new ResultMessage(ResultMessage.SUCCESS, loginCode);
    }

    @Override
    public Venue getCurrentVenue() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            User user = (User) auth.getPrincipal();
            String venueCode = user.getUsername();
            return venueRepository.findFirstByVenueId(venueCode);
        } else {
            return null;
        }
    }

    @Override
    public String getVenueRegisterEmail(String loginCode) {
        User user = userRepository.findUserByUsername(loginCode);
        if (user != null) {
            return user.getEmail();
        }
        return "";
    }

    @Override
    public ResultMessage modifyAddress(AddressBean addressBean) {
        try {
            Venue venue = venueRepository.findOne(addressBean.id);
            if (!venue.getEditPermit()) {
                return new ResultMessage(ResultMessage.ERROR, "尚未通过信息审核，无法进行新的修改");
            }

            venue.setEditPermit(false);
            venueRepository.save(venue);

            VenuePermission venuePermission = new VenuePermission(venue, addressBean.newAddress, new Timestamp(System.currentTimeMillis()), PermissionStatus.WAIT_LIST, PermissionType.VENUE_ADDRESS);
            venuePermissionRepository.save(venuePermission);
            return new ResultMessage(ResultMessage.SUCCESS, "已提交修改请求，等待管理员审核");
        } catch (Exception e) {
            return new ResultMessage(ResultMessage.ERROR, "修改失败");
        }
    }

    @Override
    public ResultMessage modifyPassword(PasswordBean passwordBean) {
        Venue venue = getCurrentVenue();

        if (passwordBean.newPassword.equals("") || passwordBean.oldPassword.equals("") || passwordBean.confirmPassword.equals("")) {
            return new ResultMessage(ResultMessage.ERROR, "请输入密码");
        }

        if (!passwordBean.oldPassword.equals(venue.getPassword())) {
            return new ResultMessage(ResultMessage.ERROR, "原密码错误");
        } else {
            if (!passwordBean.newPassword.equals(passwordBean.confirmPassword)) {
                return new ResultMessage(ResultMessage.ERROR, "两次密码输入不一致");
            }
        }

        venue.setPassword(passwordBean.newPassword);
        venueRepository.save(venue);

        User corrVenue = userRepository.findUserByUsername(venue.getVenueId());
        corrVenue.setPassword(passwordBean.newPassword);
        userRepository.save(corrVenue);
        return new ResultMessage(ResultMessage.SUCCESS, "修改密码成功");
    }

    @Override
    public ResultMessage modifySeats(List<SeatInfoBean> seatInfoBeans) {
        if (seatInfoBeans == null || seatInfoBeans.size() == 0) {
            return new ResultMessage(ResultMessage.ERROR, "请设置座位信息");
        }

        // 判断当前场馆有没有活动
        Venue venue = venueRepository.findOne(seatInfoBeans.get(0).venueId);
        Timestamp cur = new Timestamp(System.currentTimeMillis());
        List<ShowPlan> showPlans = showPlanRepository.findShowPlansByVenueAndStartTimeAfterAndEndTimeAfter(venue, cur, cur);

        if (showPlans.size() != 0) {
            return new ResultMessage(ResultMessage.ERROR, "存在未完成活动，不能修改位置信息");
        }

        // 将之前的座位全部设为invalid
        List<Seat> beforeSeats = seatRepository.findAllByVenue(venue);
        for (Seat seat : beforeSeats) {
            seat.setValid(false);
        }

        seatRepository.save(beforeSeats);
        // 加入新的
        List<Seat> newSeats = new ArrayList<>();
        for (SeatInfoBean seatInfoBean : seatInfoBeans) {
            for (int curRow = 0; curRow < seatInfoBean.row; curRow++) {
                for (int curCol = 0; curCol < seatInfoBean.col; curCol++) {
                    newSeats.add(new Seat(venue, seatInfoBean.section, (curRow + 1), (curCol + 1), true));
                }
            }
        }

        seatRepository.save(newSeats);

        return new ResultMessage(ResultMessage.SUCCESS, "修改成功");
    }

    @Override
    public List<SeatInfoBean> getSeatsInfo(Venue venue) {
        List<Seat> seats = seatRepository.findAllByVenueAndIsValid(venue, true);
        Map<String, List<Seat>> sectionMap = seats.stream().collect(Collectors.groupingBy(Seat::getSeatSection));

        List<SeatInfoBean> seatInfoBeans = new ArrayList<>();
        for (Map.Entry<String, List<Seat>> entry : sectionMap.entrySet()) {
            Map<Integer, Long> cnt = entry.getValue().stream().collect(Collectors.groupingBy(Seat::getSeatRow, Collectors.counting()));

            seatInfoBeans.add(new SeatInfoBean(venue.getId(), entry.getKey(), cnt.keySet().size(), cnt.get(1).intValue()));
        }

        return seatInfoBeans;
    }
}
