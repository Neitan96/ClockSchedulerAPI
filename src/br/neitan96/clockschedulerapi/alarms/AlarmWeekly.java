package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.Util;

/**
 * Created by Neitan96 on 08/09/15.
 */
public class AlarmWeekly implements ClockAlarm{

    public final static String LABEL = "Semanal";

    protected final int weekDay, hour, minute, second;

    /**
     * @param weekDay Numero do dia semana entre 0-6, sendo 0 domingo e 6 sábado
     * @param hour    Hora entre 0 e 23
     * @param minute  Minutos entre 0 e 59
     * @param second  Segundos entre 0 e 59
     */
    public AlarmWeekly(int weekDay, int hour, int minute, int second){
        this.weekDay = weekDay;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    /**
     * @param weekDay Numero do dia semana entre 1-7, sendo 1 domingo e 7 sábado
     * @param hour Hora entre 0 e 23
     * @param minute Minutos entre 0 e 59
     */
    public AlarmWeekly(int weekDay, int hour, int minute){
        this(weekDay, hour, minute, 0);
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

        while(calendar.getWeek() != weekDay)
            calendar.addDay();

        return calendar.getTimeInMillis();
    }

    @Override
    public String toString() {
        return String.format("%s|%s %02d:%02d:%02d", LABEL, Util.getWeek(weekDay), hour, minute, second);
    }
}
