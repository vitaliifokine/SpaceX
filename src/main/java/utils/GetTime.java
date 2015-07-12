package utils;

import java.util.Calendar;

/**
 * This class designed to provide time with shift or current time
 */

public class GetTime {

    public static String current() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        String time;
        if (minute < 10) {time = hour + ":0" + minute;}else {time = hour + ":" + minute;}
        return time;
    }

    public static String withShift(int hours, int minutes) {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY) + hours;
        int minute = now.get(Calendar.MINUTE) + minutes;
        if (minute > 59) {
            minute = minute - 60;
            hour ++;
        }
        if (hour > 23) {
            hour = hour - 24;
        }
        String time;
        if (minute < 10) {time = hour + ":0" + minute;}else {time = hour + ":" + minute;}
        return time;
    }
}
