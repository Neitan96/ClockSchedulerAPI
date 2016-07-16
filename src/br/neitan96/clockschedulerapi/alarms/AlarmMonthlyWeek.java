package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.Util;

import java.util.Calendar;

/**
 * Created by Neitan96 on 07/09/15.
 */
public class AlarmMonthlyWeek implements ClockAlarm{

    public final static String LABEL = "MensalSemana";

    protected final int weekCount, week, hour, minute, second;

    public AlarmMonthlyWeek(int weekCount, int week, int hour, int minute, int second){
        this.weekCount = weekCount;
        this.week = week;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public AlarmMonthlyWeek(int weekCount, int week, int hour, int minute){
        this(weekCount, week, hour, minute, 0);
    }

    @Override
    public long nextAfter(long miliseconds){
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setSecond(second);
        calendar.setMilisecond(0);

        if(calendar.getTimeInMillis() < miliseconds)
            calendar.addDay();

        while(calendar.getWeek() != week)
            calendar.addDay();

        int count = 0;
        while(calendar.get(Calendar.WEEK_OF_MONTH) != weekCount){
            if(++count >= 24) return -1;
            calendar.addDay(7);
        }

        return calendar.getTimeInMillis();
    }

    @Override
    public String toString(){
        return String.format("%s|%02d %s %02d:%02d:%02d",
                LABEL, weekCount, Util.getWeek(week), hour, minute, second);
    }

}
