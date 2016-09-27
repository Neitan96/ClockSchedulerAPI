package br.com.nathanalmeida.clockschedulerapi.alarms;

import br.com.nathanalmeida.clockschedulerapi.util.ClockLang;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Neitan96 on 21/08/2016.
 */
public class AlarmMulti implements ClockAlarm{

    protected final ClockAlarm[] alarms;

    public AlarmMulti(ClockAlarm... alarms){
        this.alarms = alarms;
    }

    @Override
    public long nextAfter(long miliseconds){
        long nextAfter = -1;

        for(ClockAlarm alarm : alarms){
            long next = alarm.nextAfter(miliseconds);
            nextAfter = nextAfter < 0 || next < nextAfter ? next : nextAfter;
        }

        return nextAfter;
    }

    @Override
    public String toString(){
        List<String> alarms = Arrays.stream(this.alarms)
                .map(ClockAlarm::toString).collect(Collectors.toList());
        return String.format("%s|%s",
                ClockLang.ALARM_MULTI.getMessage()[0],
                String.join(", ", alarms));
    }
}
