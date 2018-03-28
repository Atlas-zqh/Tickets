package com.keenan.Tickets.util;

import com.keenan.Tickets.bean.BriefSeatBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keenan on 27/03/2018
 */
public class ParseSeatString {
    public static final List<BriefSeatBean> parseFormString(String seats) {
        List<BriefSeatBean> seatBeans = new ArrayList<>();
        String[] ss = seats.replaceAll("\\s*", "").split(",");
        for (String s : ss) {
            BriefSeatBean seatBean = new BriefSeatBean();
            String[] s1 = s.split("排");
            seatBean.row = Integer.valueOf(s1[0]);
            String[] s2 = s1[1].split("座");
            seatBean.col = Integer.valueOf(s2[0]);
            seatBeans.add(seatBean);
        }

        return seatBeans;
    }
}
