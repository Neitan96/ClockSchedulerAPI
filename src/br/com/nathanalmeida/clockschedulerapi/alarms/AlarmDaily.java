package br.com.nathanalmeida.clockschedulerapi.alarms;

import br.com.nathanalmeida.clockschedulerapi.util.ClockCalendar;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;

/**
 * Created by Neitan96 on 11/06/2016.
 */
public class AlarmDaily implements ClockAlarm{

    protected final int hour, minute, second;

    /**
     * @param hour   Hora entre 0 e 23
     * @param minute Minutos entre 0 e 59
     * @param second Segundos entre 0 e 59
     */
    public AlarmDaily(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    /**
     * @param hour   Hora entre 0 e 23
     * @param minute Minutos entre 0 e 59
     */
    public AlarmDaily(int hour, int minute){
        this(hour, minute, 0);
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

        return calendar.getTimeInMillis();
    }

    @Override
    public String toString(){
        return String.format("%s|%02d:%02d:%02d",
                ClockLang.ALARM_DAILY.getMessage()[0],
                hour, minute, second);
    }

}
