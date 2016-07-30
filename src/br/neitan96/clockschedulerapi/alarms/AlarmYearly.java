package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.Util;

/**
 * Created by Neitan96 on 08/09/15.
 */
public class AlarmYearly implements ClockAlarm{

    public final static String LABEL = "Anual";

    protected final int day, month, hour, minute, second;

    /**
     * @param month  Mês entre 0 e 11
     * @param day    Dia entre 1 e 31(podendo ser menos depedendo do mês)
     * @param hour   Hora entre 0 e 23
     * @param minute Minutos entre 0 e 59
     * @param second Segundos entre 0 e 59
     */
    public AlarmYearly(int day, int month, int hour, int minute, int second){
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    /**
     * @param month Mês entre 0 e 11
     * @param day Dia entre 1 e 31(podendo ser menos depedendo do mês)
     * @param hour Hora entre 0 e 23
     * @param minute Minutos entre 0 e 59
     */
    public AlarmYearly(int day, int month, int hour, int minute){
        this(day, month, hour, minute, 0);
    }

    @Override
    public long nextAfter(long miliseconds){
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setSecond(second);
        calendar.setMilisecond(0);

        while(calendar.getTimeInMillis() < miliseconds){
            calendar.addYear();
            calendar.setMonth(month);
            calendar.setDay(day);
        }

        return calendar.getTimeInMillis();
    }

    @Override
    public String toString() {
        return String.format("%s|%02d %s %02d:%02d:%02d",
                LABEL, day, Util.getMonth(month), hour, minute, second);
    }

}
