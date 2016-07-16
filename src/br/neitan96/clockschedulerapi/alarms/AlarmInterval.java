package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;

/**
 * Created by Neitan96 on 02/10/15.
 */
public class AlarmInterval implements ClockAlarm{

    public final static String LABEL = "Intervalo";

    protected final int intervalSecond;

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
        return String.format("%s|%d", LABEL, intervalSecond);
    }

}
