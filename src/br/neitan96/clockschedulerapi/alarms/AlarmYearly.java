package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.Util;

/**
 * Created by Neitan96 on 08/09/15.
 */
public class AlarmYearly implements ClockAlarm{

    public final static String LABEL = "Anual";

    protected final int month, day, hour, minute, second;

    public AlarmYearly(int month, int day, int hour, int minute, int second){
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public AlarmYearly(int month, int day, int hour, int minute){
        this(month, day, hour, minute, 0);
    }

    @Override
    public long nextAfter(long miliseconds){
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setSecond(second);
        calendar.setMilisecond(0);

        while(calendar.getTimeInMillis() < miliseconds){
            calendar.addYear();
            calendar.setMonth(month);
            calendar.setDay(day);
        }

        return calendar.getTimeInMillis();
    }

    @Override
    public String toString() {
        return String.format("%s|%02d %s %02d:%02d:%02d",
                LABEL, day, Util.getMonth(month), hour, minute, second);
    }

}
