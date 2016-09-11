package br.com.nathanalmeida.clockschedulerapi.alarms;

import br.com.nathanalmeida.clockschedulerapi.util.ClockCalendar;
import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;
import br.com.nathanalmeida.clockschedulerapi.util.Util;

/**
 * Created by Neitan96 on 08/09/15.
 */
public class AlarmYearly implements ClockAlarm{

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
     * @param month  Mês entre 0 e 11
     * @param day    Dia entre 1 e 31(podendo ser menos depedendo do mês)
     * @param hour   Hora entre 0 e 23
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
    public String toString(){
        return String.format("%s|%02d %s %02d:%02d:%02d",
                ClockLang.ALARM_YEARLY.getMessage()[0],
                day, Util.getMonth(month), hour, minute, second);
    }

}
