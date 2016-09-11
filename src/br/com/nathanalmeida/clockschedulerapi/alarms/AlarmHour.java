package br.com.nathanalmeida.clockschedulerapi.alarms;

import br.com.nathanalmeida.clockschedulerapi.util.ClockCalendar;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;

/**
 * Created by Neitan96 on 10/09/15.
 */
public class AlarmHour implements ClockAlarm{

    protected final int minute, second;

    /**
     * @param minute Minutos entre 0 e 59
     * @param second Segundos entre 0 e 59
     */
    public AlarmHour(int minute, int second){
        this.minute = minute;
        this.second = second;
    }

    /**
     * @param minute Minutos entre 0 e 59
     */
    public AlarmHour(int minute){
        this(minute, 0);
    }

    @Override
    public long nextAfter(long miliseconds){
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setMinute(minute);
        calendar.setSecond(second);
        calendar.setMilisecond(0);

        if(calendar.getTimeInMillis() < miliseconds)
            calendar.addHour();

        return calendar.getTimeInMillis();
    }

    @Override
    public String toString(){
        return String.format("%s|%02d:%02d",
                ClockLang.ALARM_HOUR.getMessage()[0],
                minute, second);
    }
}
