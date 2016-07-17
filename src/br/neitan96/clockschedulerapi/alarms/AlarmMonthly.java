package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;

/**
 * Created by Neitan96 on 07/09/15.
 */
public class AlarmMonthly implements ClockAlarm{

    public final static String LABEL = "Mensal";

    protected final int day, hour, minute, second;

    public AlarmMonthly(int day, int hour, int minute, int second){
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public AlarmMonthly(int day, int hour, int minute){
        this(day, hour, minute, 0);
    }

    @Override
    public long nextAfter(long miliseconds){
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setDay(day);
        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setSecond(second);
        calendar.setMilisecond(0);

        if(calendar.getTimeInMillis() < miliseconds){
            calendar.addMonth(1);
            calendar.setDay(day);
        }

        return calendar.getTimeInMillis();
    }

    @Override
    public String toString(){
        return String.format("%s|%02d %02d:%02d:%02d", LABEL, day, hour, minute, second);
    }

}