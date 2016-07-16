package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;

/**
 * Created by Neitan96 on 11/06/2016.
 */
public class AlarmDaily implements ClockAlarm{

    public final static String LABEL = "Diario";

    protected final int hour, minute, second;

    public AlarmDaily(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public AlarmDaily(int hour, int minute){
        this(hour, minute, 0);
    }

    @Override
    public long nextAfter(long miliseconds){
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setMinute(second);
        calendar.setMilisecond(0);

        if(calendar.getTimeInMillis() < miliseconds)
            calendar.addDay();

        return calendar.getTimeInMillis();
    }

    @Override
    public String toString(){
        return String.format("%s|%02d:%02d:%02d", LABEL, hour, minute, second);
    }

}
