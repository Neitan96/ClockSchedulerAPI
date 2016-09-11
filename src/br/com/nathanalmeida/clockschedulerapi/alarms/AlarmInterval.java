package br.com.nathanalmeida.clockschedulerapi.alarms;

import br.com.nathanalmeida.clockschedulerapi.util.ClockCalendar;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;

/**
 * Created by Neitan96 on 02/10/15.
 */
public class AlarmInterval implements ClockAlarm{

    protected final int intervalSecond;

    /**
     * @param intervalSecond Intervalo em segundos
     */
    public AlarmInterval(int intervalSecond){
        this.intervalSecond = intervalSecond;
    }

    @Override
    public long nextAfter(long miliseconds){
        ClockCalendar calendar = new ClockCalendar(miliseconds);
        calendar.setMilisecond(0);
        while(calendar.getTimeInMillis() < miliseconds)
            calendar.addSecond(intervalSecond);
        return calendar.getTimeInMillis();
    }

    @Override
    public String toString(){
        return String.format("%s|%d",
                ClockLang.ALARM_INTERVAL.getMessage()[0],
                intervalSecond);
    }

}
