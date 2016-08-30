package br.neitan96.clockschedulerapi.alarms;

import br.neitan96.clockschedulerapi.util.ClockCalendar;
import br.neitan96.clockschedulerapi.util.ClockLang;
import br.neitan96.clockschedulerapi.util.Util;

import java.util.Calendar;

/**
 * Created by Neitan96 on 07/09/15.
 */
public class AlarmMonthlyWeek implements ClockAlarm{

    protected final int weekCount, week, hour, minute, second;

    /**
     * @param weekCount Numero da semana.
     *                  Exemplo:
     *                  Se for 1 é a primeira semana do mês
     *                  Se for 2 é a segunda semana do mês
     *                  Se for 3 é a terceira semana do mês
     * @param week      Numero do dia semana entre 1-7, sendo 1 domingo e 7 sábado
     * @param hour      Hora entre 0 e 23
     * @param minute    Minutos entre 0 e 59
     * @param second    Segundos entre 0 e 59
     */
    public AlarmMonthlyWeek(int weekCount, int week, int hour, int minute, int second){
        this.weekCount = weekCount;
        this.week = week;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    /**
     * @param weekCount Numero da semana.
     *                  Exemplo:
     *                  Se for 1 é a primeira semana do mês
     *                  Se for 2 é a segunda semana do mês
     *                  Se for 3 é a terceira semana do mês
     * @param week      Numero do dia semana entre 1-7, sendo 1 domingo e 7 sábado
     * @param hour      Hora entre 0 e 23
     * @param minute    Minutos entre 0 e 59
     */
    public AlarmMonthlyWeek(int weekCount, int week, int hour, int minute){
        this(weekCount, week, hour, minute, 0);
    }

    @Override
    public long nextAfter(long miliseconds){
        ClockCalendar calendar = new ClockCalendar(miliseconds);

        calendar.setHour(hour);
        calendar.setMinute(minute);
        calendar.setSecond(second);
        calendar.setMilisecond(0);

        int count = 0;
        int month = calendar.getMonth();
        while(calendar.getTimeInMillis() < miliseconds
                || calendar.getWeek() != week
                || calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) != weekCount){

            if(++count > 24) return -1;

            if(calendar.getMonth() == month){
                calendar.addMonth();
                month = calendar.getMonth();
            }
            calendar.setWeek(week);
            calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekCount);
        }

        return calendar.getTimeInMillis();
    }

    @Override
    public String toString(){
        return String.format("%s|%02d %s %02d:%02d:%02d",
                ClockLang.ALARM_MONTHLYWEEK.getMessage()[0],
                weekCount, Util.getWeek(week), hour, minute, second);
    }

}
