package com.example.chris_000.charitytime.model;

import android.net.Uri;

/**
 * Created by chris_000 on 9/26/2015.
 */
public class AlarmModel {
    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    public long alarmId;
    public int timeHour;
    public int timeMinute;
    private boolean repeatingDays[];
    public boolean repeatWeekly;
    public Uri alarmTone;
    public String alarmName;
    public boolean isEnabled;

    public AlarmModel() {
        repeatingDays= new boolean[7];
    }

    public void setRepeatingDay(int dayOfWeek, boolean repeat) {
        repeatingDays[dayOfWeek] = repeat;
    }

    public boolean getRepeatingDay(int dayOfWeek) {
        return repeatingDays[dayOfWeek];
    }

}
