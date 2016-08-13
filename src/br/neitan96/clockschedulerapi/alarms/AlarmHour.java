package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;

/**
 * Created by Neitan96 on 10/09/15.
 */
public class AlarmHour implements ClockAlarm{

    public final static String LABEL = "Horario";

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
        return String.format("%s|%02d:%02d", LABEL, minute, second);
    }
}
