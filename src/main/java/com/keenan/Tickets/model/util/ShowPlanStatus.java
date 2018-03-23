package com.keenan.Tickets.model.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author keenan on 19/03/2018
 */
public enum ShowPlanStatus {
    ABUNDANCE("有票"), FULL("无票"), FINISHED("已结束");

    private String text;
    private static final Map<String, ShowPlanStatus> stringToEnum = new HashMap<>();

    static {
        for (ShowPlanStatus showPlanStatus : values()) {
            stringToEnum.put(showPlanStatus.toString(), showPlanStatus);
        }
    }

    public static ShowPlanStatus fromString(String showPlanStatusStr) {
        return stringToEnum.get(showPlanStatusStr);
    }

    ShowPlanStatus(String text) {
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
