package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;

/**
 * Created by Neitan96 on 08/09/15.
 */
public class AlarmDate implements ClockAlarm{

    public final static String LABEL = "Data";

    protected final int year, month, day, hour, minute, second;

    public AlarmDate(int year, int month, int day, int hour, int minute, int second){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public AlarmDate(int year, int month, int day, int hour, int minute){
        this(year, month, day, hour, minute, 0);
    }

    @Override
    public long nextAfter(long miliseconds){
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setSecond(second);
        calendar.setMilisecond(0);

        if(calendar.getTimeInMillis() < miliseconds)
            return (long) -1;

        return calendar.getTimeInMillis();
    }

    @Override
    public String toString(){
        return String.format("%s|%02d/%02d/%d %02d:%02d:%02d",
                LABEL, day, month, year, hour, minute, second);
    }

}
