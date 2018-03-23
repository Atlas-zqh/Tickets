package com.keenan.Tickets.model.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author keenan on 19/03/2018
 */
public enum ShowPlanType {
    CONCERT("演唱会"), LIVE("音乐现场"), DRAMA("戏剧"), MUSICAL("音乐会"), DANCE("歌舞剧"), OPERA("话剧");

    private String text;

    private static final Map<String, ShowPlanType> stringToEnum = new HashMap<>();

    static {
        for (ShowPlanType showPlanType : values()) {
            stringToEnum.put(showPlanType.toString(), showPlanType);
        }
    }

    public static ShowPlanType fromString(String showPlanTypeStr) {
        return stringToEnum.get(showPlanTypeStr);
    }

    ShowPlanType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return text;
    }
}
