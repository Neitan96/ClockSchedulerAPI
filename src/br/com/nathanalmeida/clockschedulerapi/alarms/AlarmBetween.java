package br.com.nathanalmeida.clockschedulerapi.alarms;

import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;

/**
 * Created by Neitan96 on 22/08/2016.
 */
public class AlarmBetween implements ClockAlarm{

    protected final ClockAlarm start, end, alarm;

    /**
     * @param start Inicio do intervalo
     * @param end   Fim do intervalo
     * @param alarm Alarm
     */
    public AlarmBetween(ClockAlarm start, ClockAlarm end, ClockAlarm alarm){
        this.start = start;
        this.end = end;
        this.alarm = alarm;
    }

    @Override
    public long nextAfter(long miliseconds){

        long milisecondsTemp = miliseconds;
        long start;
        long end;
        long next;
        long nextOld = -1;

        do{
            start = this.start.nextAfter(milisecondsTemp);
            if(start <= 0) return -1;

            end = this.end.nextAfter(start);
            next = alarm.nextAfter(start);

            if(milisecondsTemp > (miliseconds + 157788000000L)
                    || end < 0 || next < 0 || next == nextOld)
                return -1;

            milisecondsTemp = start + 1;
            nextOld = next;
        }while(next < start || next > end);

        return next;
    }

    @Override
    public String toString(){
        return String.format("%s|%s, %s, %s",
                ClockLang.ALARM_BETWEEN.getMessage()[0],
                start.toString(), end.toString(), alarm.toString());
    }

}
