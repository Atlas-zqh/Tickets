package test;

import org.apache.tomcat.util.security.MD5Encoder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author keenan on 19/03/2018
 */
public class Main {
    public static void main(String[] args) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Timestamp start = new Timestamp(System.currentTimeMillis());
        Timestamp end = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        String weekDay = weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日_HH:mm");
        String startStr = df.format(start);
        String date = startStr.split("_")[0];
        String startTime = startStr.split("_")[1];
        String endTime = df.format(end).split("_")[1];

        String time = date + " " + weekDay + " " + startTime + "-" + endTime;

        System.out.println(time);
    }
}
